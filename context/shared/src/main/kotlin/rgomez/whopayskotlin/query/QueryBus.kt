package rgomez.whopayskotlin.query

import arrow.core.Either
import kotlin.reflect.KClass

interface QueryBus {
    fun <Q, R : Any> ask(query: Q, successType: KClass<R>): Either<Throwable, R>
}