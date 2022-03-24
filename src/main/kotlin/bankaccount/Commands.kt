package bankaccount

import java.time.LocalDateTime

sealed interface BankAccountCommand
data class MakeDeposit(val amount: Double, val dateTime: LocalDateTime) : BankAccountCommand
data class MakeWithdraw(val amount: Double, val dateTime: LocalDateTime) : BankAccountCommand
