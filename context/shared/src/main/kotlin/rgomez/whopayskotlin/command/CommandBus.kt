package rgomez.whopayskotlin.command

import arrow.core.Either

interface CommandBus {
    fun <C, R> execute(command: C): Either<Throwable, R>
}