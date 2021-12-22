package rgomez.whopayskotlin.event

import java.math.BigDecimal
import java.time.ZonedDateTime

class RepaymentPerformedEvent(
    val id: String,
    val concept: String,
    val amount: BigDecimal,
    val currency: String,
    val date: ZonedDateTime,
    val payer: String,
    val creditor: String
) : DomainEvent(id) {
    override fun type(): String = "groups.charges.repayment.performed"
}