package community.flock.workshop.common

import community.flock.workshop.error.DomainError
import community.flock.workshop.error.UserNotFound
import community.flock.workshop.exception.AppException
import community.flock.workshop.exception.BusinessException
import community.flock.workshop.exception.DomainException
import community.flock.workshop.exception.TechnicalException
import community.flock.workshop.exception.ValidationException
import org.springframework.http.ResponseEntity.badRequest
import org.springframework.http.ResponseEntity.internalServerError
import org.springframework.http.ResponseEntity.notFound
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

data class ErrorReason(
    val reason: String,
)

@ControllerAdvice
class AppExceptionHandler {
    @ExceptionHandler(AppException::class)
    fun handleException(exception: AppException) =
        exception.run {
            when (this) {
                is BusinessException -> handle()
                is TechnicalException -> internalServerError().body(ErrorReason(message))
            }
        }
}

private fun BusinessException.handle() =
    when (this) {
        is DomainException -> error.handle()
        is ValidationException -> badRequest().body(ErrorReason(message))
    }

private fun DomainError.handle() =
    when (this) {
        is UserNotFound -> notFound().build<ErrorReason>()
    }
