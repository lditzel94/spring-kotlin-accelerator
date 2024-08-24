package dev.luciano.accelerator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan("dev.luciano.accelerator")
class SpringKotlinAccelerator

fun main(args: Array<String>) {
    runApplication<SpringKotlinAccelerator>(*args)
}
