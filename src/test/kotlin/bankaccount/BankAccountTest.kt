package bankaccount

import DepositMade
import WithdrawMade
import WithdrawRefused
import eventsourcing.inmemory.InMemoryDecider
import org.assertj.core.api.Assertions
import java.time.LocalDateTime
import kotlin.test.Test

class BankAccountTest {

    private val bankAccount = InMemoryDecider(bankAccountDecider)

    @Test
    fun `should accept deposit`() {
        val now = LocalDateTime.now()
        val command = MakeDeposit(1_000.00, now)

        val events = bankAccount.handle(command)

        Assertions.assertThat(events).containsExactly(DepositMade(1_000.00, now))
    }

    @Test
    fun `should accept withdraw`() {
        val now = LocalDateTime.now()
        bankAccount.handle(MakeDeposit(1_000.00, now))
        val command = MakeWithdraw(1_00.00, now)

        val events = bankAccount.handle(command)

        Assertions.assertThat(events).containsExactly(WithdrawMade(1_00.00, now))
    }

    @Test
    fun `should refuse to withdraw under 0`() {
        val now = LocalDateTime.now()
        val command = MakeWithdraw(1_000.00, now)

        val events = bankAccount.handle(command)

        Assertions.assertThat(events).containsExactly(WithdrawRefused(1_000.00, now))
    }

}