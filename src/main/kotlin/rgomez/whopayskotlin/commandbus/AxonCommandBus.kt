package rgomez.whopayskotlin.commandbus

import arrow.core.Either
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.stereotype.Component
import rgomez.whopayskotlin.command.CommandBus

@Component
class AxonCommandBus(private val commandGateway: CommandGateway) : CommandBus {
  override fun <C, R> execute(command: C): Either<Throwable, R> =
    Either.catch { commandGateway.sendAndWait(command) }
}
