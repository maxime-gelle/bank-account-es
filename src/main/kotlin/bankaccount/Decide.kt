package bankaccount

import BankAccountEvent
import DepositMade

fun decide(state: BankAccountState, command: BankAccountCommand): List<BankAccountEvent> =
    when(command) {
        is MakeDeposit -> listOf(DepositMade(command.amount, command.dateTime))
    }