package dev.luciano.accelerator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories

@SpringBootApplication
@EnableR2dbcRepositories
@ConfigurationPropertiesScan("dev.luciano.accelerator")
class SpringKotlinAccelerator

fun main(args: Array<String>) {
    runApplication<SpringKotlinAccelerator>(*args)
}
