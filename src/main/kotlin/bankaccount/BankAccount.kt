package bankaccount

import BankAccountEvent

class BankAccount {

    private val pastEvents = emptyList<BankAccountEvent>()

    fun handle(command: BankAccountCommand): List<BankAccountEvent> {
        val state = evolve(pastEvents)
        return decide(state, command)
    }

}