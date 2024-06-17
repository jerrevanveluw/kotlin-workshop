package community.flock.workshop.app.environment

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.startupcheck.IsRunningStartupCheckStrategy
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.postgresql.PostgreSQLContainer

@Testcontainers
interface WithContainers {
    companion object {
        private val postgresContainer: PostgreSQLContainer =
            PostgreSQLContainer("postgres:$POSTGRESQL_VERSION")
                .withDatabaseName("postgres")
                .withUsername("postgres")
                .withPassword("postgres")
                .withStartupCheckStrategy(IsRunningStartupCheckStrategy())

        @JvmStatic
        @BeforeAll
        fun beforeAll() {
            postgresContainer.start()
        }

        @JvmStatic
        @AfterAll
        fun afterAll() {
            postgresContainer.stop()
        }

        @JvmStatic
        @DynamicPropertySource
        fun registerDBContainer(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url") { postgresContainer.connectionString }
        }
    }
}

private val PostgreSQLContainer.connectionString
    get() = "jdbc:postgresql://$host:${getMappedPort(5432)}/postgres"
