import java.time.LocalDateTime

sealed interface BankAccountEvent;
data class DepositMade(val amount: Double, val dateTime: LocalDateTime) : BankAccountEvent
data class WithdrawMade(val amount: Double, val dateTime: LocalDateTime) : BankAccountEvent
