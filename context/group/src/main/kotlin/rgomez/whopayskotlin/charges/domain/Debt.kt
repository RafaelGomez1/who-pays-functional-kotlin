package rgomez.whopayskotlin.charges.domain

import rgomez.whopayskotlin.event.DebtCreatedEvent

data class Debt(
    override val id: ChargeId,
    override val concept: Concept,
    override val amount: Money,
    override val date: Date,
    override val payer: Payer,
    val debtors: List<Debtor>
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
            debtors: List<Debtor>,
        ) = Debt(id, concept, amount, date, payer, debtors)
            .also { it.pushDebtCreatedEvent() }
    }

    private fun pushDebtCreatedEvent() = this.pushEvent(
        DebtCreatedEvent(
            id = id.value,
            concept = concept.value,
            amount = amount.amount,
            currency = amount.currency.name,
            date = date.value,
            payer = payer.value,
            debtors = debtors.map { it.value }
        )
    )

}