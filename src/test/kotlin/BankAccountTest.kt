import bankaccount.MakeDeposit
import bankaccount.decide
import bankaccount.evolve
import org.assertj.core.api.Assertions
import java.time.LocalDateTime
import kotlin.test.Test

class BankAccountTest {

    @Test
    fun `should accept deposit`() {
        val now = LocalDateTime.now()
        val command = MakeDeposit(1_000.00, now)

        val state = evolve(emptyList())
        val events = decide(state, command)

        Assertions.assertThat(events).containsExactly(DepositMade(1_000.00, now))
    }

}