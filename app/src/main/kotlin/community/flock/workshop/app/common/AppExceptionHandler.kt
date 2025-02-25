package community.flock.workshop.app.common

import community.flock.workshop.app.exception.AppException
import community.flock.workshop.app.exception.BusinessException
import community.flock.workshop.app.exception.DomainException
import community.flock.workshop.app.exception.TechnicalException
import community.flock.workshop.app.exception.ValidationException
import community.flock.workshop.domain.error.DomainError
import community.flock.workshop.domain.error.UserNotFound
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.badRequest
import org.springframework.http.ResponseEntity.internalServerError
import org.springframework.http.ResponseEntity.notFound
import org.springframework.http.ResponseEntity.status
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

data class ErrorReason(
    val reason: String,
)

@ControllerAdvice
class AppExceptionHandler {
    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ErrorReason> =
        status(INTERNAL_SERVER_ERROR)
            .body(ErrorReason(INTERNAL_SERVER_ERROR.reasonPhrase))

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
