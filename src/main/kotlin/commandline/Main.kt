package commandline

import bankaccount.MakeDeposit
import bankaccount.MakeWithdraw
import bankaccount.bankAccountDecider
import eventsourcing.eventstore.EventStoreDecider
import eventsourcing.withEventhandlers.WithEventHandlers
import eventstore.FileEventStore
import satement.Statement
import java.io.File
import java.time.LocalDateTime


val eventStore = FileEventStore(File("/tmp/eventStore"))
val statement = Statement()
val bankAccount = WithEventHandlers(
    EventStoreDecider(bankAccountDecider, eventStore), listOf(statement)
)
val statementPrinter = StatementPrinter()

fun main() {

    generateSequence { readLine() }
        .forEach { line ->
            when {
                line == "statement" -> statementPrinter.print(statement.state)
                line.startsWith("deposit") -> makeDeposit(line)
                line.startsWith("withdraw") -> makeWithdraw(line)
                else -> println("Unknown command $line")
            }
        }
}

fun makeDeposit(line: String) {
    val (_cmd, amount) = line.split(" ")
    bankAccount.handle(MakeDeposit(amount.toDouble(), LocalDateTime.now()))
}

fun makeWithdraw(line: String) {
    val (_cmd, amount) = line.split(" ")
    bankAccount.handle(MakeWithdraw(amount.toDouble(), LocalDateTime.now()))
}