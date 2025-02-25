package community.flock.workshop.app.common

import arrow.core.Either
import arrow.core.getOrElse
import community.flock.workshop.app.exception.AppException
import community.flock.workshop.app.exception.DomainException
import community.flock.workshop.app.exception.TechnicalException
import community.flock.workshop.app.exception.ValidationException
import community.flock.workshop.domain.error.DomainError
import community.flock.workshop.domain.error.Error
import community.flock.workshop.domain.error.TechnicalError
import community.flock.workshop.domain.error.ValidationError
import community.flock.workshop.domain.error.ValidationErrors

fun <R> Either<Error, R>.mapError() =
    mapLeft {
        when (it) {
            is TechnicalError -> TechnicalException(cause = it.cause, message = it.message)
            is ValidationError -> ValidationException(errors = listOf(it))
            is ValidationErrors -> ValidationException(errors = it.errors)
            is DomainError -> DomainException(error = it)
        }
    }

fun <R> Either<AppException, R>.handle() = getOrElse { throw it }
