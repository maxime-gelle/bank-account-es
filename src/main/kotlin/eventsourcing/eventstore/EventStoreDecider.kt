package eventsourcing.eventstore

import eventsourcing.CommandHandler
import eventsourcing.Decider

open class EventStoreDecider<Command, Event, State>(
    private val decider: Decider<Command, Event, State>,
    private val eventStore: EventStore<Event>
) : CommandHandler<Command, Event> {

    override fun handle(command: Command): List<Event> {
        val state = eventStore.loadEvents().fold(decider.initialState, decider.evolve)
        val newEvents = decider.decide(command, state)
        eventStore.appendEvents(newEvents)
        return newEvents
    }

}