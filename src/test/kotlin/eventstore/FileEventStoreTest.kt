package eventstore

import DepositMade
import WithdrawMade
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import java.io.File
import java.time.LocalDateTime
import kotlin.test.Test

internal class FileEventStoreTest {

    @BeforeEach
    fun beforeEach() {
        File("/tmp/eventStore-test").delete()
    }

    @AfterEach
    fun afterEach() {
        File("/tmp/eventStore-test").delete()
    }

    private val eventStore = FileEventStore(File("/tmp/eventStore-test"))

    @Test
    fun `should store events`() {
        val events1 = listOf(
            DepositMade(100.0, LocalDateTime.of(2022, 7, 27, 9, 50)),
            WithdrawMade(50.0, LocalDateTime.of(2021, 7, 27, 9, 50))
        )
        eventStore.appendEvents(events1)
        val events2 = listOf(
            DepositMade(100.0, LocalDateTime.of(2024, 7, 27, 9, 50))
        )
        eventStore.appendEvents(events2)

        val events = eventStore.loadEvents()

        assertThat(events).isEqualTo(events1 + events2)
    }

}