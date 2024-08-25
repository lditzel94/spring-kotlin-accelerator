package dev.luciano.accelerator.configuration.cloud.aws

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.web.reactive.function.client.WebClient

@Configuration
@Profile("dev", "prod")
class AwsCognitoClientConfiguration(
    @Value("\${aws.cognito.client-id}") private val clientId: String,
    @Value("\${aws.cognito.client-secret}") private val clientSecret: String,
    @Value("\${aws.cognito.user-pool-id}") private val userPoolId: String,
    @Value("\${aws.cognito.region}") private val region: String,
    private val webClient: WebClient,
) {

    fun getAccessToken(): String {
        val tokenResponse = webClient
            .post()
            .uri("https://$userPoolId.auth.$region.amazoncognito.com/oauth2/token")
            .bodyValue(
                "grant_type=client_credentials&client_id=$clientId&client_secret=$clientSecret"
            )
            .retrieve()
            .bodyToMono(Map::class.java)
            .block()

        return tokenResponse?.get("access_token").toString()
    }
}