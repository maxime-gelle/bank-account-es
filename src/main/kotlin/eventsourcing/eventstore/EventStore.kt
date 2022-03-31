package eventsourcing.eventstore

interface EventStore<Event> {
    fun loadEvents(): List<Event>
    fun appendEvents(events: List<Event>)
}