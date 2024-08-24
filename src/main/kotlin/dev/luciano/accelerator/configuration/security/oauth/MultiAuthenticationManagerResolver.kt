package dev.luciano.accelerator.configuration.security.oauth

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import jakarta.annotation.PostConstruct
import java.util.Base64
import org.springframework.context.annotation.Profile
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.ReactiveAuthenticationManagerResolver
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtDecoders
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoders
import org.springframework.security.oauth2.server.resource.authentication.JwtReactiveAuthenticationManager
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

@Component
@Profile("dev", "prod")
class MultiAuthenticationManagerResolver(
    private val oAuthConfigProps: OAuthConfigProps,
) : ReactiveAuthenticationManagerResolver<ServerWebExchange> {

    private val authenticationManagers: MutableMap<String, ReactiveAuthenticationManager> = mutableMapOf()

    private lateinit var knownIssuers: List<String>

    @PostConstruct
    fun initialize() {
        knownIssuers = oAuthConfigProps.knownIssuers
        knownIssuers.forEach { issuer ->
            // Initialize authentication managers for known issuers
            addManager(authenticationManagers, issuer).subscribe()
        }
    }

    override fun resolve(exchange: ServerWebExchange): Mono<ReactiveAuthenticationManager> =
        extractToken(exchange)?.let {
            Mono.justOrEmpty(it)
                .flatMap { decodedToken ->
                    // Decode the JWT token and determine the issuer
                    Mono.fromCallable { decodeJwt(decodedToken) }
                        .subscribeOn(Schedulers.boundedElastic())
                }.flatMap { jwt ->
                    val issuer = jwt.issuer.toString()

                    // Check if the issuer is in the known list
                    if (knownIssuers.contains(issuer)) {
                        return@flatMap authenticationManagers[issuer]?.let { manager ->
                            Mono.just(manager)
                        } ?: addManager(authenticationManagers, issuer)
                    }

                    // Handle unknown issuers
                    Mono.error(IllegalArgumentException("Unsupported issuer"))
                }
        } ?: throw IllegalArgumentException("Invalid JWT token")

    private fun extractToken(exchange: ServerWebExchange): String? {
        val authorizationHeader = exchange.request.headers.getFirst("Authorization")
        return authorizationHeader?.substringAfter("Bearer ")
    }

    private fun decodeJwt(token: String): Jwt {
        val issuer = extractIssuer(token)
        val jwtDecoder: JwtDecoder = JwtDecoders.fromIssuerLocation(issuer)
        return jwtDecoder.decode(token)
    }

    private fun extractIssuer(token: String): String {
        // Extract the payload part of the JWT and decode it
        val parts = token.split(".")
        if (parts.size < 2) {
            throw IllegalArgumentException("Invalid JWT token")
        }
        val payload = String(Base64.getUrlDecoder().decode(parts[1]))
        // You can use a JSON parser to extract the "iss" (issuer) claim
        val claims = jacksonObjectMapper().readValue(payload, Map::class.java) as Map<String, Any>
        return claims["iss"].toString()
    }

    private fun addManager(
        authenticationManagers: MutableMap<String, ReactiveAuthenticationManager>, issuer: String
    ): Mono<JwtReactiveAuthenticationManager> {
        return Mono.fromCallable { ReactiveJwtDecoders.fromIssuerLocation(issuer) }
            .subscribeOn(Schedulers.boundedElastic())
            .map { jwtDecoder: ReactiveJwtDecoder -> JwtReactiveAuthenticationManager(jwtDecoder) }
            .doOnNext { authenticationManager: JwtReactiveAuthenticationManager ->
                authenticationManagers[issuer] = authenticationManager
            }
    }
}