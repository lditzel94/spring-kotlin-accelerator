package dev.luciano.accelerator.configuration.security.authorization

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.core.annotation.Order
import org.springframework.http.HttpMethod.GET
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.config.web.server.invoke
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers.pathMatchers

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@Profile("dev", "prod")
class AuthorizationSecurityConfiguration {

    @Bean
    @Order(2)
    fun configure(http: ServerHttpSecurity): SecurityWebFilterChain =
        http {
            authorizeExchange {
                authorize(
                    pathMatchers(GET, "/api-docs/*", "/webjars/swagger-ui/", "/health/*"),
                    permitAll
                )
                authorize(anyExchange, authenticated)
            }
        }
}