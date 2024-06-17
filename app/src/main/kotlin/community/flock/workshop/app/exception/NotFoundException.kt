package community.flock.workshop.app.exception

open class AppException(
    override val message: String,
) : RuntimeException(message)

open class NotFoundException(
    what: String,
) : AppException("$what Not found")

class UserNotFoundException : NotFoundException("User")
