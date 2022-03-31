package es.inmemory

import es.CommandHandler
import es.Decider

open class InMemoryDecider<Command, Event, State>(
    private val decider: Decider<Command, Event, State>
) : CommandHandler<Command, Event> {
    private var state: State = decider.initialState

    override fun handle(command: Command): List<Event> {
        val newEvents = decider.decide(command, state)
        state = newEvents.fold(state) { state, event -> decider.evolve(state, event) }
        return newEvents
    }

}