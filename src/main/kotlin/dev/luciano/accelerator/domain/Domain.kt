package dev.luciano.accelerator.domain

import dev.luciano.accelerator.configuration.logger.CompanionLogger
import java.util.UUID

data class Domain(
    val id: UUID,
    val data: String,
) {
    companion object : CompanionLogger() {
        operator fun invoke() =
            Domain(id = UUID.randomUUID(), data = "")
                .log { info("Domain object provided {}", it) }
    }

}