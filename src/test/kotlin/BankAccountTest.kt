import bankaccount.*
import org.assertj.core.api.Assertions
import java.time.LocalDateTime
import kotlin.test.Test

class BankAccountTest {

    val bankAccount = BankAccount()

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
        val command = MakeWithdraw(1_000.00, now)

        val events = bankAccount.handle(command)

        Assertions.assertThat(events).containsExactly(WithdrawMade(1_000.00, now))
    }

}