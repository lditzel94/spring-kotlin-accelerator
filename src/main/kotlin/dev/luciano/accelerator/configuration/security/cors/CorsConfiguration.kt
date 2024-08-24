package dev.luciano.accelerator.configuration.security.cors

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.CorsConfigurationSource
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource

@Configuration
@Profile("dev", "prod")
class CorsConfiguration(
    private val props: CorsConfigProps,
) {

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource =
        UrlBasedCorsConfigurationSource().apply {
            registerCorsConfiguration("/**",
                CorsConfiguration().also { cors ->
                    cors.allowedOrigins = props.allowedOrigins.splitNotBlank(',')
                    cors.allowedMethods = props.allowedMethods.splitNotBlank(',')
                    cors.allowedHeaders = props.allowedHeaders.splitNotBlank(',')
                    cors.allowCredentials = props.allowCredentials
                    cors.maxAge = props.maxAge
                }
            )
        }

    private fun String.splitNotBlank(char: Char) =
        split(char).filter { it.isNotBlank() }
}