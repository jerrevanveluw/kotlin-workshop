package community.flock.workshop.app

import community.flock.workshop.app.environment.WithContainers
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class WorkshopApplicationTests : WithContainers {
    @Test
    fun contextLoads() {
    }
}
