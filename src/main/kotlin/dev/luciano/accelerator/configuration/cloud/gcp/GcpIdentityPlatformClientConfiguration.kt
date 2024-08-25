package dev.luciano.accelerator.configuration.cloud.gcp

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.web.reactive.function.client.WebClient

@Configuration
@Profile("dev", "prod")
class GcpIdentityPlatformClientConfiguration(
    @Value("\${gcp.client-id}") private val clientId: String,
    @Value("\${gcp.client-secret}") private val clientSecret: String,
    private val webClient: WebClient,
) {

    fun getAccessToken(): String {
        val tokenResponse = webClient
            .post()
            .uri("https://oauth2.googleapis.com/token")
            .bodyValue(
                "grant_type=client_credentials&client_id=$clientId&client_secret=$clientSecret"
            )
            .retrieve()
            .bodyToMono(Map::class.java)
            .block()

        return tokenResponse?.get("access_token").toString()
    }
}