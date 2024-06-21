package community.flock.workshop.common

import arrow.core.Either
import community.flock.workshop.error.TechnicalError

suspend fun <T, R> T.catch(block: suspend T.() -> R) =
    Either.catch { block() }
        .mapLeft(TechnicalError::invoke)
