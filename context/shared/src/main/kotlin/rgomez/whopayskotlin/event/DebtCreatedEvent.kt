package rgomez.whopayskotlin.event

import java.math.BigDecimal
import java.time.ZonedDateTime

data class DebtCreatedEvent(
    val id: String,
    val concept: String,
    val amount: BigDecimal,
    val currency: String,
    val date: ZonedDateTime,
    val payer: String,
    val debtors: List<String>
) : DomainEvent(id) {
    override fun type(): String = "groups.charges.debt.created"
}
