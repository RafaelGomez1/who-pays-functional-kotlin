package rgomez.whopayskotlin.querybus

import arrow.core.Either
import kotlin.reflect.KClass
import org.axonframework.messaging.responsetypes.ResponseTypes
import org.axonframework.queryhandling.QueryGateway
import org.springframework.stereotype.Component
import rgomez.whopayskotlin.query.QueryBus

@Component
class AxonQueryBus(private val queryGateway: QueryGateway) : QueryBus {

  override fun <Q, R : Any> ask(query: Q, successType: KClass<R>): Either<Throwable, R> {
    return kotlin.runCatching { queryGateway.query(query, ResponseTypes.instanceOf(successType.java)).get() }
      .fold(
        onSuccess = { Either.Right(it) },
        onFailure = { Either.Left(it.cause!!) }
      )
  }
}
