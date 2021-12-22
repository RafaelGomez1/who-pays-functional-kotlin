package rgomez.whopayskotlin.charges.domain

import arrow.core.right
import rgomez.whopayskotlin.event.Aggregate

sealed class Charge(
    open val id: ChargeId,
    open val concept: Concept,
    open val amount: Money,
    open val date: Date,
    open val payer: Payer
) : Aggregate() {

    companion object {
        fun create(
            id: ChargeId,
            concept: Concept,
            amount: Money,
            date: Date,
            payer: Payer,
            debtors: List<Debtor>,
            creditor: Creditor
        ) = when(debtors.isEmpty()) {
            true -> Repayment.create(id, concept, amount, date, payer, creditor).right()
            false -> Debt.create(id, concept, amount, date, payer, debtors).right()
        }
    }
}

