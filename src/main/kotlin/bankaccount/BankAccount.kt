package bankaccount

import eventsourcing.Decider

val bankAccountDecider = Decider(::decide, ::evolve, initialState)

