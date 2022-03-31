package commandline

import satement.StatementLine
import java.time.format.DateTimeFormatter

class StatementPrinter {
    private val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    private val separator = " | "

    fun print(list: List<StatementLine>) {
        println("Date | Amount | Balance")
        list.forEach { line ->
            print(line.date.format(formatter))
            print(separator)
            print(line.amount)
            print(separator)
            println(line.balance)
        }
    }

}