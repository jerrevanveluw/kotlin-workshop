package community.flock.workshop.app

import com.fasterxml.jackson.databind.ObjectMapper
import community.flock.wirespec.integration.jackson.kotlin.WirespecModuleKotlin
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.http.codec.json.Jackson2JsonEncoder
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class AppConfiguration(
    @Value("\${service.notes.baseurl}")
    private val notesBaseUrl: String,
    objectMapper: ObjectMapper,
) {
    val wirespecObjectMapper: ObjectMapper = objectMapper.copy().registerModules(WirespecModuleKotlin())

    @Bean
    fun notesClient() =
        WebClient
            .builder()
            .baseUrl(notesBaseUrl)
            .exchangeStrategies(
                ExchangeStrategies
                    .builder()
                    .codecs {
                        it
                            .defaultCodecs()
                            .jackson2JsonEncoder(Jackson2JsonEncoder(wirespecObjectMapper, APPLICATION_JSON))
                        it
                            .defaultCodecs()
                            .jackson2JsonDecoder(Jackson2JsonDecoder(wirespecObjectMapper, APPLICATION_JSON))
                    }.build(),
            ).build()
}
