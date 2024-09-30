package dev.luciano.accelerator.configuration.logger

import arrow.core.Either
import org.slf4j.MDC
import reactor.util.Logger
import reactor.util.Loggers

abstract class CompanionLogger {

    private val log: Logger by lazy { Loggers.getLogger(javaClass.enclosingClass) }
    private val folder: String by lazy { javaClass.enclosingClass.packageName.substringAfterLast('.').uppercase() }

    protected suspend fun <T> T.log(block: suspend Logger.(T) -> Unit): T =
        also {
            MDC.put("folder", folder)
            MDC.put("thread", Thread.currentThread().toString())
            block(log, this)
            MDC.remove("folder")
        }

    protected suspend fun <L, R> Either<L, R>.logRight(right: suspend Logger.(R) -> Unit): Either<L, R> =
        also {
            fold({}, { r -> log { right(r) } })
        }

    protected suspend fun <L, R> Either<L, R>.logLeft(left: suspend Logger.(L) -> Unit): Either<L, R> =
        also {
            fold({ l -> log { left(l) } }, { })
        }

    protected suspend fun <L, R> Either<L, R>.logEither(
        left: suspend Logger.(L) -> Unit,
        right: suspend Logger.(R) -> Unit,
    ): Either<L, R> =
        also {
            fold({ l -> log { left(l) } }, { r -> log { right(r) } })
        }
}