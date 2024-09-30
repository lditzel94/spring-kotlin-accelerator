package dev.luciano.accelerator.adapter.db.r2dbc

import arrow.core.raise.catch
import arrow.core.raise.either
import dev.luciano.accelerator.configuration.error.Failure
import dev.luciano.accelerator.configuration.error.Failure.GenericFailure
import dev.luciano.accelerator.domain.DomainModel
import dev.luciano.accelerator.domain.DomainRepository
import kotlinx.coroutines.flow.toList
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

interface R2dbcRepository : CoroutineCrudRepository<DomainModel, Long>

@Repository(value = "coroutine_r2dbc_repository")
class CoroutineR2dbcRepositoryAdapter(
    private val r2dbcRepository: R2dbcRepository,
) : DomainRepository {
    override suspend fun findAll() = either<Failure, List<DomainModel>> {
        catch({
            r2dbcRepository
                .findAll()
                .toList()
                .map { DomainModel(it.id, it.data) }
        }) { raise(GenericFailure(it)) }
    }
}