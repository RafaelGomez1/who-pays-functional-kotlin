package rgomez.whopayskotlin.publisher

import org.axonframework.eventhandling.gateway.EventGateway
import org.springframework.stereotype.Component
import rgomez.whopayskotlin.event.DomainEvent
import rgomez.whopayskotlin.event.publisher.DomainEventPublisher

@Component
class AxonDomainEventPublisher(private val eventGateway: EventGateway) : DomainEventPublisher {

  override fun <T : DomainEvent> publish(event: T) {
    publish(listOf(event))
  }

  override fun <T : DomainEvent> publish(events: List<T>) {
    if (events.isNotEmpty()) {
      eventGateway.publish(events)
    }
  }
}
