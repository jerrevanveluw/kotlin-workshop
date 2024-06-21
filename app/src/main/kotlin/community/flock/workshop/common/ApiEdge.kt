package community.flock.workshop.common

import arrow.core.Either
import arrow.core.getOrElse
import community.flock.workshop.error.DomainError
import community.flock.workshop.error.Error
import community.flock.workshop.error.TechnicalError
import community.flock.workshop.error.ValidationError
import community.flock.workshop.error.ValidationErrors
import community.flock.workshop.exception.AppException
import community.flock.workshop.exception.DomainException
import community.flock.workshop.exception.TechnicalException
import community.flock.workshop.exception.ValidationException

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
