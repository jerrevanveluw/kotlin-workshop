package community.flock.workshop.app.common

import community.flock.workshop.app.exception.NotFoundException
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.status
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class AppExceptionHandler {
    @ExceptionHandler(RuntimeException::class)
    fun handleException(e: RuntimeException): ResponseEntity<Error> =
        status(INTERNAL_SERVER_ERROR)
            .body(Error(INTERNAL_SERVER_ERROR.reasonPhrase))

    @ExceptionHandler(NotFoundException::class)
    fun handleException(e: NotFoundException): ResponseEntity<Error> =
        status(NOT_FOUND)
            .body(Error(e.message))
}
