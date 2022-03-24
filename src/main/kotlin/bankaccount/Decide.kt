package bankaccount

import BankAccountEvent
import DepositMade
import WithdrawMade
import WithdrawRefused

fun decide(command: BankAccountCommand, state: BankAccountState): List<BankAccountEvent> =
    when(command) {
        is MakeDeposit -> listOf(DepositMade(command.amount, command.dateTime))
        is MakeWithdraw -> if (state.balance >= command.amount) {
            listOf(WithdrawMade(command.amount, command.dateTime))
        } else {
            listOf(WithdrawRefused(command.amount, command.dateTime))
        }
    }
