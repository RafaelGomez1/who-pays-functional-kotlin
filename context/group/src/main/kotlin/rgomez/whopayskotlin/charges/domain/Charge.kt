package rgomez.whopayskotlin.charges.domain

import java.time.ZonedDateTime
import rgomez.whopayskotlin.event.Aggregate
import rgomez.whopayskotlin.members.domain.MemberId

sealed class Charge(
    open val id: ChargeId,
    open val concept: Concept,
    open val quantity: Quantity,
    open val currency: Currency,
    open val date: Date,
    open val payer: MemberId
) : Aggregate() {

    data class Debt(debtors: List<MemberId>) : Charge(
        id = id,
        concept = concept,
        quantity = quantity,
        currency = currency,
        date = date,
        payer = payer
    )

    data class Repayment(creditor: MemberId) : Charge(
        id = id,
        concept = concept,
        quantity = quantity,
        currency = currency,
        date = date,
        payer = payer
    )
}


@JvmInline
value class ChargeId(val value: String) {
    init {
        require(regex.matches(value))
    }

    companion object {
        private val regex = "/^\\{?[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}\u200C\u200B\\}?\$/".toRegex()
    }
}

@JvmInline
value class Quantity(val value: Double)

@JvmInline
value class Concept(val value: String)

@JvmInline
value class Currency(val value: String)

@JvmInline
value class Date(val value: ZonedDateTime)

enum class