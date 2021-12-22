package rgomez.whopayskotlin.charges.domain

import rgomez.whopayskotlin.event.RepaymentPerformedEvent

data class Repayment(
    override val id: ChargeId,
    override val concept: Concept,
    override val amount: Money,
    override val date: Date,
    override val payer: Payer,
    val creditor: Creditor
) : Charge(
    id = id,
    concept = concept,
    amount = amount,
    date = date,
    payer = payer
) {
    companion object {
        fun create(
            id: ChargeId,
            concept: Concept,
            amount: Money,
            date: Date,
            payer: Payer,
            creditor: Creditor
        ) = Repayment(id, concept, amount, date, payer, creditor)
            .also { it.pushRepaymentPerformedEvent() }
    }

    private fun pushRepaymentPerformedEvent() =
        this.pushEvent(
            RepaymentPerformedEvent(
                id = id.value,
                concept = concept.value,
                amount = amount.amount,
                currency = amount.currency.name,
                date = date.value,
                payer = payer.value, creditor = creditor.value
            )
        )
}
