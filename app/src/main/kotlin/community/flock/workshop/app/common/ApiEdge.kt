package community.flock.workshop.app.common

import arrow.core.Either
import arrow.core.getOrElse
import arrow.core.left
import arrow.core.raise.Raise
import arrow.core.raise.either
import arrow.core.right
import community.flock.wirespec.kotlin.Wirespec
import community.flock.workshop.app.exception.DomainException
import community.flock.workshop.app.exception.TechnicalException
import community.flock.workshop.app.exception.ValidationException
import community.flock.workshop.domain.error.DomainError
import community.flock.workshop.domain.error.Error
import community.flock.workshop.domain.error.TechnicalError
import community.flock.workshop.domain.error.ValidationError
import community.flock.workshop.domain.error.ValidationErrors
import kotlin.experimental.ExperimentalTypeInference

@OptIn(ExperimentalTypeInference::class)
inline fun <T : Any, R : Any> handle(
    producer: Producer<T, R>,
    @BuilderInference block: Raise<Error>.() -> T,
): R =
    with(producer) {
        either(block)
            .mapError()
            .getOrElse { throw it }
            .produce()
    }

fun <R> Either<Error, R>.mapError() = mapLeft(Error::mapError)

fun Error.mapError() =
    when (this) {
        is TechnicalError -> TechnicalException(cause = cause, message = message)
        is ValidationError -> ValidationException(errors = listOf(this))
        is ValidationErrors -> ValidationException(errors = errors)
        is DomainError -> DomainException(error = this)
    }

@OptIn(ExperimentalTypeInference::class)
inline fun <A : Any, T : Any, R : Wirespec.Response<*>> wirespec(
    producer: Producer<A, T>,
    response200: (T) -> R,
    recover: (DomainError) -> Either<Error, R> = { it.left() },
    @BuilderInference block: Raise<Error>.() -> A,
): R =
    with(producer) {
        either(block)
            .map { it.produce() }
    }.map(response200)
        .fold({
            when (it) {
                is DomainError -> recover(it)
                else -> it.left()
            }
        }, { it.right() })
        .mapError()
        .getOrElse { throw it }
