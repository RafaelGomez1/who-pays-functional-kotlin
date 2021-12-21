package rgomez.whopayskotlin.balance.domain

import java.time.ZonedDateTime
import rgomez.whopayskotlin.event.Aggregate
import rgomez.whopayskotlin.group.domain.GroupId
import rgomez.whopayskotlin.members.domain.MemberId

data class Balance(
    val id: BalanceId,
    val groupId: GroupId,
    val debts: List<Debt>
) : Aggregate() {

    companion object {
        fun create(
            id: BalanceId,
            groupId: GroupId,
            debts: List<Debt>
        ) = Balance(id, groupId, debts) // TODO -> add event
    }
}

data class Debt(
    val debtor: MemberId,
    val quantity: Quantity,
    val concept: Concept,
    val currency: Currency,
    val date: Date,
    val creditor: MemberId
) {
    @JvmInline
    value class Quantity(val value: Double)

    @JvmInline
    value class Concept(val value: String)

    @JvmInline
    value class Currency(val value: String)

    @JvmInline
    value class Date(val value: ZonedDateTime)
}

@JvmInline
value class BalanceId(val value: String) {
    init {
        require(regex.matches(value))
    }

    companion object {
        private val regex = "/^\\{?[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}\u200C\u200B\\}?\$/".toRegex()
    }
}