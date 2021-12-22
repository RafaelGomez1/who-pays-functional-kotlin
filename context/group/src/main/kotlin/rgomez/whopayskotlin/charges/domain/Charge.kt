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

    data class Debt(
        override val id: ChargeId,
        override val concept: Concept,
        override val quantity: Quantity,
        override val currency: Currency,
        override val date: Date,
        override val payer: MemberId,
        val debtors: List<MemberId>
    ) : Charge(
        id = id,
        concept = concept,
        quantity = quantity,
        currency = currency,
        date = date,
        payer = payer
    )

    data class Repayment(
        override val id: ChargeId,
        override val concept: Concept,
        override val quantity: Quantity,
        override val currency: Currency,
        override val date: Date,
        override val payer: MemberId,
        val creditor: MemberId
        ) : Charge(
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

enum class Currency {
    EUR
}

@JvmInline
value class Date(val value: ZonedDateTime)