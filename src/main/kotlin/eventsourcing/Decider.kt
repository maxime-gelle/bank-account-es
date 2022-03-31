package eventsourcing

data class Decider<Command, Event, State>(
    val decide: (Command, State) -> List<Event>,
    val evolve: (State, Event) -> State,
    val initialState: State
)

interface CommandHandler<Command, Event> {
    fun handle(command: Command): List<Event>
}