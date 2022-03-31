package bankaccount

import es.Decider

val bankAccountDecider = Decider(::decide, ::evolve, initialState)

