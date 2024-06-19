package community.flock.workshop.error

sealed class UserDomainError(
    message: String,
) : DomainError(message)

class UserNotFound(
    id: String,
) : UserDomainError("User with id: $id, not found")
