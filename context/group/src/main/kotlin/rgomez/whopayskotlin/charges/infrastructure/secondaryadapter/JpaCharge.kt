package rgomez.whopayskotlin.charges.infrastructure.secondaryadapter

import java.math.BigDecimal
import java.time.ZonedDateTime
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import rgomez.whopayskotlin.charges.domain.Charge
import rgomez.whopayskotlin.charges.domain.ChargeId
import rgomez.whopayskotlin.charges.domain.Concept
import rgomez.whopayskotlin.charges.domain.Creditor
import rgomez.whopayskotlin.charges.domain.Date
import rgomez.whopayskotlin.charges.domain.Debt
import rgomez.whopayskotlin.charges.domain.Debtor
import rgomez.whopayskotlin.charges.domain.Money
import rgomez.whopayskotlin.charges.domain.Payer
import rgomez.whopayskotlin.charges.domain.Repayment

@Document(collection = "groups")
data class JpaCharge(

    @Id
    val id: String,
    val concept: String,
    val quantity: BigDecimal,
    val currency: String,
    val date: ZonedDateTime,
    val payer: String,
    val type: String,
    val debtors: List<String> = emptyList(),
    val creditor: String = ""
) {

    fun toCharge() =
        when (Type.valueOf(type)) {
            Type.DEBT ->
                Debt(
                    id = ChargeId(value = id),
                    concept = Concept(value = concept),
                    amount = Money(amount = quantity, currency = Money.Currency.valueOf(currency)),
                    date = Date(value = date),
                    payer = Payer(value = payer),
                    debtors = debtors.map { Debtor(it) }
                )
            Type.REPAYMENT ->
                Repayment(
                    id = ChargeId(value = id),
                    concept = Concept(value = concept),
                    amount = Money(amount = quantity, currency = Money.Currency.valueOf(currency)),
                    date = Date(value = date),
                    payer = Payer(value = payer),
                    creditor = Creditor(value = creditor)
                )
        }

    enum class Type {
        DEBT, REPAYMENT
    }
}

internal fun Charge.toJpaCharge() = JpaCharge(
    id = id.value,
    concept = concept.value,
    quantity = amount.amount,
    currency = amount.currency.name,
    date = date.value,
    payer = payer.value,
    type = this.findType().name,
    debtors = this.findDebtor(),
    creditor = this.findCreditor().value
)

private fun Charge.findType(): JpaCharge.Type =
    when(this) {
        is Repayment -> JpaCharge.Type.REPAYMENT
        is Debt -> JpaCharge.Type.DEBT
    }

private fun Charge.findDebtor(): List<String> =
    when(this) {
        is Repayment -> emptyList()
        is Debt -> debtors.map { it.value }
    }

private fun Charge.findCreditor(): Creditor =
    when(this) {
        is Repayment -> this.creditor
        is Debt -> Creditor.empty()
    }