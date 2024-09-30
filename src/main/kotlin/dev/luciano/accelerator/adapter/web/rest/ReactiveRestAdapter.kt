package dev.luciano.accelerator.adapter.web.rest

import dev.luciano.accelerator.domain.DomainModel
import dev.luciano.accelerator.domain.DomainRepository
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/reactive")
class ReactiveRestAdapter(
    @Qualifier("reactive_r2dbc_repository")
    private val repository: DomainRepository,
) {
    @GetMapping
    suspend fun getRecords(): List<DomainModel> =
        repository.findAll()
            .fold(
                ifLeft = { emptyList() },
                ifRight = { it }
            )
}