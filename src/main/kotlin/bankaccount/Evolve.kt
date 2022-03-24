package bankaccount

import BankAccountEvent

class BankAccountState

fun evolve(state: BankAccountState, events: BankAccountEvent): BankAccountState =
    BankAccountState()