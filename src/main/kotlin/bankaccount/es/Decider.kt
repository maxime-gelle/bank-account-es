package bankaccount.es

interface Decider<Command, Event, State> {
    val decide: (Command, State) -> List<Event>
    val evolve: (State, Event) -> State
    val initialState: State
    fun handle(command: Command): List<Event>
}