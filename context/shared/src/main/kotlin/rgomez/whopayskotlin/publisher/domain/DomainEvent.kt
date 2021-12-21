package rgomez.whopayskotlin.publisher.domain

abstract class DomainEvent(aggregateId: String) {
    private var meta: DomainEventMeta = DomainEventMeta(aggregateId)

    abstract fun type(): String

    fun aggregateId(): String {
        return meta.aggregateId()
    }

    fun occurredOn(): java.time.ZonedDateTime {
        return meta.occurredOn()
    }

    fun service(): String = meta.source().service
    fun project(): String = meta.source().project
    fun tenant(): String = meta.source().tenant

    fun setSource(service: String, project: String, tenant: String) {
        meta = meta.copy(source = Source(service = service, project = project, tenant = tenant))
    }
}

internal data class DomainEventMeta(
    private val aggregateId: String,
    private val sequenceId: String = java.util.UUID.randomUUID().toString(),
    private val version: String = "1.0.0",
    private val timestamp: java.time.ZonedDateTime = java.time.ZonedDateTime.now(java.time.ZoneId.of("UTC")),
    private var source: Source = Source(service = "", tenant = "", project = "")
) {
    fun aggregateId(): String {
        return aggregateId
    }

    fun occurredOn(): java.time.ZonedDateTime {
        return timestamp
    }

    fun source(): Source {
        return source
    }
}

data class Source(val service: String, val project: String, val tenant: String)