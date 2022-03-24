package bankaccount

import BankAccountEvent
import DepositMade
import WithdrawMade

fun decide(command: BankAccountCommand, state: BankAccountState): List<BankAccountEvent> =
    when(command) {
        is MakeDeposit -> listOf(DepositMade(command.amount, command.dateTime))
        is MakeWithdraw -> listOf(WithdrawMade(command.amount, command.dateTime))
    }
