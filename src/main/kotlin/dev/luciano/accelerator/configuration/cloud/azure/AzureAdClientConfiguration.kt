package dev.luciano.accelerator.configuration.cloud.azure

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.web.reactive.function.client.WebClient

@Configuration
@Profile("dev", "prod")
class AzureAdClientConfiguration(
    @Value("\${azure.ad.client-id}") private val clientId: String,
    @Value("\${azure.ad.client-secret}") private val clientSecret: String,
    @Value("\${azure.ad.tenant-id}") private val tenantId: String,
    private val webClient: WebClient,
) {

    fun getAccessToken(): String {
        val tokenResponse = webClient
            .post()
            .uri("https://login.microsoftonline.com/$tenantId/oauth2/v2.0/token")
            .bodyValue(
                "grant_type=client_credentials&client_id=$clientId&client_secret=$clientSecret&scope=https://graph.microsoft.com/.default"
            )
            .retrieve()
            .bodyToMono(Map::class.java)
            .block()

        return tokenResponse?.get("access_token").toString()
    }
}