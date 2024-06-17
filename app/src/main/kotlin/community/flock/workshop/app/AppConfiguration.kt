package community.flock.workshop.app

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class AppConfiguration(
    @Value("\${service.notes.baseurl}")
    private val notesBaseUrl: String,
) {
    @Bean
    fun notesClient() = WebClient.create(notesBaseUrl)
}
