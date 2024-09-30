package dev.luciano.accelerator.adapter.web.rest

import dev.luciano.accelerator.configuration.logger.CompanionLogger
import dev.luciano.accelerator.domain.DomainModel
import dev.luciano.accelerator.domain.DomainRepository
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/coroutine")
class CoroutineRestAdapter(
    @Qualifier("coroutine_r2dbc_repository")
    private val repository: DomainRepository,
) {
    companion object : CompanionLogger()

    @GetMapping
    suspend fun getRecords(): List<DomainModel> =
        repository
            .findAll()
            .logEither(
                left = { error("{}", it.message) },
                right = { info("{}", it) }
            )
            .fold(
                ifLeft = { emptyList() },
                ifRight = { it }
            )
}