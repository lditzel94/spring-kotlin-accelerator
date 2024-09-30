package dev.luciano.accelerator.configuration.error

sealed interface Failure {
    val message: String

    data class GenericFailure(val e: Throwable) : Failure {
        override val message: String = e.localizedMessage
    }
}