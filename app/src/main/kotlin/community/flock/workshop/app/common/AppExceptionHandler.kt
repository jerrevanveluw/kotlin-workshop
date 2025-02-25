package community.flock.workshop.app.common

import community.flock.workshop.app.exception.AppException
import community.flock.workshop.app.exception.ValidationException
import community.flock.workshop.domain.error.DomainError
import community.flock.workshop.domain.error.UserNotFound
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.ResponseEntity
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
    fun handleException(e: AppException): ResponseEntity<ErrorReason> =
        when (e) {
            is ValidationException -> status(BAD_REQUEST).body(ErrorReason(e.message))
        }

    @ExceptionHandler(DomainError::class)
    fun handleDomainError(e: DomainError): ResponseEntity<ErrorReason> =
        when (e) {
            is UserNotFound -> status(NOT_FOUND).body(ErrorReason(e.message))
        }
}
