package rgomez.whopayskotlin.charges.infrastructure.secondaryadapter

import java.time.ZonedDateTime
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import rgomez.whopayskotlin.charges.domain.Charge
import rgomez.whopayskotlin.charges.domain.ChargeId
import rgomez.whopayskotlin.charges.domain.Concept
import rgomez.whopayskotlin.charges.domain.Currency
import rgomez.whopayskotlin.charges.domain.Date
import rgomez.whopayskotlin.charges.domain.Quantity
import rgomez.whopayskotlin.members.domain.MemberId

@Document(collection = "groups")
data class JpaCharge(

    @Id
    val id: String,
    val concept: String,
    val quantity: Double,
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
                Charge.Debt(
                    id = ChargeId(value = id),
                    concept = Concept(value = concept),
                    quantity = Quantity(value = quantity),
                    currency = Currency.valueOf(currency),
                    date = Date(value = date),
                    payer = MemberId(value = payer),
                    debtors = debtors.map { MemberId(it) }
                )
            Type.REPAYMENT ->
                Charge.Repayment(
                    id = ChargeId(value = id),
                    concept = Concept(value = concept),
                    quantity = Quantity(value = quantity),
                    currency = Currency.valueOf(currency),
                    date = Date(value = date),
                    payer = MemberId(value = payer),
                    creditor = MemberId(value = creditor)
                )
        }

    enum class Type {
        DEBT, REPAYMENT
    }
}

internal fun Charge.toJpaCharge() = JpaCharge(
    id = id.value,
    concept = concept.value,
    quantity = quantity.value,
    currency = currency.name,
    date = date.value,
    payer = payer.value,
    type = this.findType().name,
    debtors = this.findDebtor(),
    creditor = this.findCreditor().value
)

private fun Charge.findType(): JpaCharge.Type =
    when(this) {
        is Charge.Repayment -> JpaCharge.Type.REPAYMENT
        is Charge.Debt -> JpaCharge.Type.DEBT
    }

private fun Charge.findDebtor(): List<String> =
    when(this) {
        is Charge.Repayment -> emptyList()
        is Charge.Debt -> debtors.map { it.value }
    }

private fun Charge.findCreditor(): MemberId =
    when(this) {
        is Charge.Repayment -> this.creditor
        is Charge.Debt -> MemberId("")
    }


