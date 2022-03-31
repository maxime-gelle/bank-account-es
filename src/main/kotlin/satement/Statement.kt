package satement

import BankAccountEvent
import DepositMade
import WithdrawMade
import eventsourcing.withEventhandlers.EventHandler
import eventstore.FileEventStore
import java.time.LocalDateTime

class Statement(private val eventStore: FileEventStore) : EventHandler<BankAccountEvent> {
    var state = listOf<StatementLine>()
    private var balance = 0.0

    fun replay() {
        state = emptyList()
        balance = 0.0
        eventStore.loadEvents().forEach(this::handle)
    }

    override fun handle(event: BankAccountEvent) {
        this.balance = evolveBalance(balance, event)
        this.state = evolveStatement(state, event)
    }

    private fun evolveStatement(statements: List<StatementLine>, event: BankAccountEvent) =
        when(event) {
            is DepositMade -> statements + StatementLine(date = event.dateTime, amount = event.amount, balance = this.balance)
            is WithdrawMade -> statements + StatementLine(date = event.dateTime, amount = -event.amount, balance = this.balance)
            else -> statements
        }

    private fun evolveBalance(balance: Double, event: BankAccountEvent) =
        when(event) {
            is DepositMade -> balance + event.amount
            is WithdrawMade -> balance - event.amount
            else -> balance
        }

}

data class StatementLine(
    val date: LocalDateTime,
    val amount: Double,
    val balance: Double
)
