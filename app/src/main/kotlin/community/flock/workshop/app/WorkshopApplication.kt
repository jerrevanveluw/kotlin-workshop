package community.flock.workshop.app

import community.flock.wirespec.integration.spring.kotlin.configuration.EnableWirespec
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@EnableWirespec
@SpringBootApplication
class WorkshopApplication

fun main(args: Array<String>) {
    runApplication<WorkshopApplication>(*args)
}
