package rgomez.whopayskotlin.charges.domain.create

import arrow.core.Either
import arrow.core.right
import javax.inject.Named
import rgomez.whopayskotlin.charges.domain.ChargeRepository

@Named
class ChargeCreator(private val repository: ChargeRepository) {

    fun invoke(): Either<CreateChargeError, Unit> = Unit.right()

}

sealed class CreateChargeError(throwable: Throwable) : RuntimeException(throwable) {
    data class Unknown(val throwable: Throwable) : CreateChargeError(throwable)
}