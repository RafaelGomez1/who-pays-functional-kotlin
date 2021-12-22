package rgomez.whopayskotlin.event.publisher

import rgomez.whopayskotlin.event.DomainEvent

interface DomainEventPublisher {
    fun <E : DomainEvent> publish(event: E)
    fun <E : DomainEvent> publish(events: List<E>)
}