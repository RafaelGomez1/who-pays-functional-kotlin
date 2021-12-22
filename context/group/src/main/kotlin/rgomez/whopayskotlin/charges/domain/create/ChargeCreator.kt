package rgomez.whopayskotlin.charges.domain.create

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.right
import javax.inject.Named
import rgomez.whopayskotlin.charges.domain.Charge
import rgomez.whopayskotlin.charges.domain.ChargeId
import rgomez.whopayskotlin.charges.domain.ChargeRepository
import rgomez.whopayskotlin.charges.domain.Concept
import rgomez.whopayskotlin.charges.domain.Creditor
import rgomez.whopayskotlin.charges.domain.Date
import rgomez.whopayskotlin.charges.domain.Debtor
import rgomez.whopayskotlin.charges.domain.Money
import rgomez.whopayskotlin.charges.domain.Payer
import rgomez.whopayskotlin.event.publisher.DomainEventPublisher

@Named
class ChargeCreator(private val repository: ChargeRepository, private val publisher: DomainEventPublisher) {

    fun invoke(
        id: ChargeId,
        concept: Concept,
        amount: Money,
        date: Date,
        payer: Payer,
        debtors: List<Debtor>,
        creditor: Creditor
    ): Either<CreateChargeError, Unit> =
        Charge.create(id, concept, amount, date, payer, debtors, creditor)
            .flatMap { charge -> charge.save() }
            .flatMap { charge -> charge.publishEvents() }

    private fun Charge.save(): Either<CreateChargeError, Charge> =
        Either.catch { repository.save(this) }
            .mapLeft { error -> CreateChargeError.Unknown(error) }
            .map { this }

    private fun Charge.publishEvents(): Either<CreateChargeError, Unit> =
        Either.catch { publisher.publish(this.pullEvents()) }
            .mapLeft { error -> CreateChargeError.Unknown(error) }
            .map { Unit.right() }
}

sealed class CreateChargeError(throwable: Throwable) : RuntimeException(throwable) {
    data class Unknown(val throwable: Throwable) : CreateChargeError(throwable)
}