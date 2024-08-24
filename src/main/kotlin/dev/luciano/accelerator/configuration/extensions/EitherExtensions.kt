package dev.luciano.accelerator.configuration.extensions

fun <T> T.result(): Result<T> =
    if (this is Throwable) Result.failure(this) else Result.success(this)