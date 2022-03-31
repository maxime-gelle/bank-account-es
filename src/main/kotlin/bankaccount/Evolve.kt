package bankaccount

import BankAccountEvent
import DepositMade
import WithdrawMade
import WithdrawRefused
import java.security.InvalidParameterException

data class BankAccountState(
    val balance: Double
)

val initialState = BankAccountState(balance = 0.0)
fun evolve(state: BankAccountState, event: BankAccountEvent): BankAccountState =
    when (event) {
        is DepositMade -> state.copy(balance = state.balance + event.amount)
        is WithdrawMade -> state.copy(balance = state.balance - event.amount)
        is WithdrawRefused -> state
        else -> throw InvalidParameterException("Unknown event : event")
    }
