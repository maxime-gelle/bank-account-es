package bankaccount

import BankAccountEvent

class BankAccountState

fun evolve(events: List<BankAccountEvent>): BankAccountState =
    BankAccountState()