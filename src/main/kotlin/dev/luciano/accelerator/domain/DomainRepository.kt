package dev.luciano.accelerator.domain

import arrow.core.Either
import dev.luciano.accelerator.configuration.error.Failure

fun interface DomainRepository {
    suspend fun findAll(): Either<Failure, List<DomainModel>>
}