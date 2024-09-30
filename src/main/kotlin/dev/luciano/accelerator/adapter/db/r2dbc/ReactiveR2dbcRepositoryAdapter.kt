package dev.luciano.accelerator.adapter.db.r2dbc

import arrow.core.raise.catch
import arrow.core.raise.either
import dev.luciano.accelerator.configuration.error.Failure
import dev.luciano.accelerator.configuration.error.Failure.GenericFailure
import dev.luciano.accelerator.domain.DomainModel
import dev.luciano.accelerator.domain.DomainRepository
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

interface ReactiveR2dbcRepository : ReactiveCrudRepository<DomainModel, Long>

@Repository(value = "reactive_r2dbc_repository")
class ReactiveR2dbcRepositoryAdapter(
    private val reactiveR2dbcRepository: ReactiveR2dbcRepository,
) : DomainRepository {
    override suspend fun findAll() = either<Failure, List<DomainModel>> {
        catch({
            reactiveR2dbcRepository
                .findAll()
                .collectList()
                .awaitSingle()
        }) { raise(GenericFailure(it)) }
    }
}