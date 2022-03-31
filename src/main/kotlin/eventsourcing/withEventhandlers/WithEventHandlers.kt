package eventsourcing.withEventhandlers

import eventsourcing.CommandHandler

interface EventHandler<Event> {
    fun handle(event: Event)
}

class WithEventHandlers<Command, Event>(
    private val commandHandler: CommandHandler<Command, Event>,
    private val eventHandlers: List<EventHandler<Event>>
) : CommandHandler<Command, Event> {
    override fun handle(command: Command): List<Event> {
        val events = commandHandler.handle(command)
        eventHandlers.forEach { eventHandler ->
            events.forEach { event -> eventHandler.handle(event) }
        }
        return events
    }

}