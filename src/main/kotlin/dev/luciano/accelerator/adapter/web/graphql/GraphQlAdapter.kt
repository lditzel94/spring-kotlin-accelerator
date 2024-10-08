package dev.luciano.accelerator.adapter.web.graphql

import dev.luciano.accelerator.domain.DomainModel
import dev.luciano.accelerator.domain.DomainRepository
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller


@Controller
class GraphQlAdapter(
    @Qualifier("coroutine_r2dbc_repository")
    private val repository: DomainRepository,
) {
    @QueryMapping
    suspend fun getRecords(): List<DomainModel> =
        repository.findAll()
            .fold(
                ifLeft = { emptyList() },
                ifRight = { it }
            )
}