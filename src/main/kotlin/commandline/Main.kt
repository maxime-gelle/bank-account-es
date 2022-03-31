package commandline

import bankaccount.MakeDeposit
import bankaccount.MakeWithdraw
import bankaccount.bankAccountDecider
import eventsourcing.eventstore.EventStoreDecider
import eventstore.FileEventStore
import satement.Statement
import java.io.File
import java.time.LocalDateTime


val eventStore = FileEventStore(File("/tmp/eventStore"))
val bankAccount = EventStoreDecider(bankAccountDecider, eventStore)
val statement = Statement()
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
    val events = bankAccount.handle(MakeDeposit(amount.toDouble(), LocalDateTime.now()))
    events.forEach(statement::evolve)
}

fun makeWithdraw(line: String) {
    val (_cmd, amount) = line.split(" ")
    val events = bankAccount.handle(MakeWithdraw(amount.toDouble(), LocalDateTime.now()))
    events.forEach(statement::evolve)
}