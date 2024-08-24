package dev.luciano.accelerator.configuration.security.oauth

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.core.annotation.Order
import org.springframework.security.authentication.ReactiveAuthenticationManagerResolver
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.config.web.server.invoke
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.web.server.ServerWebExchange

@Configuration
@EnableWebFluxSecurity
@Profile("dev", "prod")
class OAuth2ResourceServerSecurityConfiguration(
    private val issuerAuthManagerResolver: ReactiveAuthenticationManagerResolver<ServerWebExchange>,
) {

    @Bean
    @Order(1)
    fun configureOAuth(http: ServerHttpSecurity): SecurityWebFilterChain =
        http {
            authorizeExchange {
                authorize(anyExchange, authenticated)
            }
            oauth2ResourceServer {
                authenticationManagerResolver = issuerAuthManagerResolver
            }
            csrf { disable() }
        }

//    private fun jwtDecoder(): ReactiveJwtDecoder =
//        NimbusReactiveJwtDecoder
//            .withJwkSetUri(properties.jwt.jwkSetUri).build()
//            .apply { setJwtValidator(jwtValidator()) }
//
//    private fun jwtValidator(): OAuth2TokenValidator<Jwt> {
//        val validators = mutableListOf<OAuth2TokenValidator<Jwt>>()
//        val issuerUri = properties.jwt.issuerUri
//        if (ObjectUtils.isEmpty(issuerUri)) {
//            validators.add(JwtIssuerValidator(issuerUri))
//        }
//        if (audiences.isNotEmpty()) {
//            validators.add(JwtClaimValidator<List<String>>(AUD) { tokenAudiences ->
//                tokenAudiences.any { it in audiences }
//            })
//        }
//        validators.add(JwtTimestampValidator())
//        return DelegatingOAuth2TokenValidator(validators)
//    }
//
//    private fun jwtAuthenticationConverter(): Converter<Jwt, Mono<AbstractAuthenticationToken>> =
//        ReactiveJwtAuthenticationConverterAdapter(
//            JwtAuthenticationConverter().apply {
//                setJwtGrantedAuthoritiesConverter(JwtGrantedAuthoritiesConverter())
//            }
//        )
}


//    private val properties: OAuth2ResourceServerProperties,
//
//    @Value("\${spring.security.oauth2.resourceserver.jwt.audiences}")
//    private val audiences: List<String>,

//                jwt {
//                    jwtDecoder = jwtDecoder()
//                    jwtAuthenticationConverter = jwtAuthenticationConverter()
//                }