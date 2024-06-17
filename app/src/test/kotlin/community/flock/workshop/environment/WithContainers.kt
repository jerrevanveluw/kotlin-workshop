package community.flock.workshop.environment

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
abstract class WithContainers {
    @Test
    fun containersAreRunning() {
        postgresql.isRunning shouldBe true
    }

    companion object {
        private val postgresql: PostgreSQLContainer<*> =
            PostgreSQLContainer("postgres:$POSTGRESQL_VERSION")
                .withUsername("supergebruiker")
                .withPassword("kapotgeheim")
                .apply { start() }

        @JvmStatic
        @BeforeAll
        fun beforeAll() {
            val postgresString = "jdbc:postgresql://${postgresql.host}:${postgresql.getMappedPort(5432)}/postgres"

            System.setProperty("spring.datasource.url", postgresString)
        }
    }
}
