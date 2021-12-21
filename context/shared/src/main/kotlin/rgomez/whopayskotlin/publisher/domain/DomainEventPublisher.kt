package rgomez.whopayskotlin.publisher.domain

interface DomainEventPublisher {
    fun <E : DomainEvent> publish(event: E)
    fun <E : DomainEvent> publish(events: List<E>)
}