package bankaccount

import BankAccountEvent

class BankAccount : Decider<BankAccountCommand, BankAccountEvent, BankAccountState>(
    decide = ::decide,
    evolve = ::evolve,
    initialState = BankAccountState()
) {}

open class Decider<Command, Event, State>(
    val decide: (Command, State) -> List<Event>,
    val evolve: (State, Event) -> State,
    val initialState: State
) {
    private var state: State = initialState

    fun handle(command: Command): List<Event> {
        val newEvents = decide(command, state);
        state = newEvents.fold(state) { state, event -> evolve(state, event) }
        return newEvents
    }

}
