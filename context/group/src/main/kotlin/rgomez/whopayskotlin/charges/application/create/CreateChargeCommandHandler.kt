package rgomez.whopayskotlin.charges.application.create

import java.math.BigDecimal
import java.time.ZonedDateTime
import javax.inject.Named
import org.axonframework.commandhandling.CommandHandler
import rgomez.whopayskotlin.charges.domain.ChargeId
import rgomez.whopayskotlin.charges.domain.create.ChargeCreator

@Named
open class CreateChargeCommandHandler(private val creator: ChargeCreator) {

    @CommandHandler
    open fun handle(command: CreateChargeCommand) =
        ChargeId.create(command.id)

}

data class CreateChargeCommand(
    val id: String,
    val concept: String,
    val amount: BigDecimal,
    val currency: String,
    val date: ZonedDateTime,
    val payer: String,
    val debtors: List<String>,
    val creditor: String
)