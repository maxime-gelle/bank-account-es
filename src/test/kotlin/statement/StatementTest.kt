package statement

import DepositMade
import WithdrawMade
import commandline.eventStore
import satement.Statement
import satement.StatementLine
import org.assertj.core.api.Assertions
import java.time.LocalDateTime
import kotlin.test.Test

class StatementTest {

    @Test
    fun `should create statement`() {
        val statement = Statement(eventStore)

        statement.handle(DepositMade(amount = 1000.0, LocalDateTime.of(2015, 12, 24, 12, 0)))
        statement.handle(WithdrawMade(300.0, LocalDateTime.of(2016, 8, 23, 12, 0)))
        statement.handle(DepositMade(100.0, LocalDateTime.of(2016, 8, 25, 12, 0)))

        Assertions.assertThat(statement.state).isEqualTo(listOf(
            StatementLine(amount = 1000.0, balance = 1000.0,date = LocalDateTime.of(2015, 12, 24, 12, 0)),
            StatementLine(amount = -300.0, balance = 700.0, date = LocalDateTime.of(2016, 8, 23, 12, 0)),
            StatementLine(amount = 100.0, balance = 800.0, date = LocalDateTime.of(2016, 8, 25, 12, 0))
        ))
    }

}