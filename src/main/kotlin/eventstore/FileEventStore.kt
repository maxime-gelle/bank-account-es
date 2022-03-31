package eventstore

import BankAccountEvent
import DepositMade
import WithdrawMade
import eventsourcing.eventstore.EventStore
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class FileEventStore(private val file: File): EventStore<BankAccountEvent> {
    init {
        file.createNewFile()
    }

    override fun loadEvents(): List<BankAccountEvent> =
        file.readLines().map(::deserializeEvent)

    private val separator = ";"

    private fun deserializeEvent(line: String): BankAccountEvent {
        val (operation, amount, date) = line.split(separator)
        return when(operation) {
            DepositMade::class.qualifiedName -> DepositMade(amount = amount.toDouble(), date.deserialize())
            WithdrawMade::class.qualifiedName -> WithdrawMade(amount = amount.toDouble(), date.deserialize())
            else -> throw IllegalStateException("Unknown event : $line")
        }
    }

    override fun appendEvents(events: List<BankAccountEvent>) {
        events.forEach(this::writeEvent)
    }

    private fun writeEvent(event: BankAccountEvent) {
        file.appendText(serializeEvent(event))
    }

    private fun serializeEvent(event: BankAccountEvent): String =
        when(event) {
            is DepositMade -> "${event::class.qualifiedName}$separator${event.amount}$separator${event.dateTime.serialize()}\n"
            is WithdrawMade -> "${event::class.qualifiedName}$separator${event.amount}$separator${event.dateTime.serialize()}\n"
            else -> throw IllegalStateException("Unknown event : $event")
        }
}

val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
private fun String.deserialize(): LocalDateTime  = LocalDateTime.parse(this, formatter)
private fun LocalDateTime.serialize(): String =  this.format(formatter)
