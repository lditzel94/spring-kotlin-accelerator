package dev.luciano.accelerator.configuration.security.oauth

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Profile

@ConfigurationProperties(prefix = "oauth.jwt")
@Profile("dev", "prod")
data class OAuthConfigProps(
    val knownIssuers: List<String>
)