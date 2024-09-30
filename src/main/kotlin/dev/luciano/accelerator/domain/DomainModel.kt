package dev.luciano.accelerator.domain

import dev.luciano.accelerator.configuration.logger.CompanionLogger
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("domain")
data class DomainModel private constructor(
    @Id val id: Long? = null,
    val data: String,
) {
    companion object : CompanionLogger() {
        suspend operator fun invoke(id: Long?, data: String) =
            DomainModel(id = id, data = data)
                .log { info("Domain object provided {}", it) }
    }
}