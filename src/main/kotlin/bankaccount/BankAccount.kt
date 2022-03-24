package bankaccount

import BankAccountEvent
import bankaccount.es.InMemoryDecider

class BankAccount : InMemoryDecider<BankAccountCommand, BankAccountEvent, BankAccountState>(
    decide = ::decide,
    evolve = ::evolve,
    initialState = BankAccountState(balance = 0.0)
) {}

