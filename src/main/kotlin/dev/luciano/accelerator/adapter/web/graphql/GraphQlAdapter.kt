package dev.luciano.accelerator.adapter.web.graphql

import dev.luciano.accelerator.domain.Domain.Companion.invoke as Domain
import dev.luciano.accelerator.domain.Domain
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import reactor.core.publisher.Mono


@Controller
class GraphQlAdapter {

    @PreAuthorize("hasRole('ADMIN')")
    @QueryMapping
    fun getRecords(): Mono<List<Domain>> = Mono.just(
        listOf(
            Domain()
        )
    )
}