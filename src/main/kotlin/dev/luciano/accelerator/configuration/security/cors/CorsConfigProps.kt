package dev.luciano.accelerator.configuration.security.cors

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Profile

@ConfigurationProperties(prefix = "cors")
@Profile("dev", "prod")
data class CorsConfigProps(
    val allowedOrigins: String,
    val allowedMethods: String,
    val allowedHeaders: String,
    val allowCredentials: Boolean,
    val maxAge: Long
) {
}