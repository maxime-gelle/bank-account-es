package bankaccount.es

open class InMemoryDecider<Command, Event, State>(
    override val decide: (Command, State) -> List<Event>,
    override val evolve: (State, Event) -> State,
    override val initialState: State
) : Decider<Command, Event, State> {
    private var state: State = initialState

    override fun handle(command: Command): List<Event> {
        val newEvents = decide(command, state);
        state = newEvents.fold(state) { state, event -> evolve(state, event) }
        return newEvents
    }

}