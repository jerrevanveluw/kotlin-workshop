package community.flock.workshop.error

import community.flock.workshop.user.model.Email

sealed class UserDomainError(
    message: String,
) : DomainError(message)

class UserNotFound(
    email: Email,
) : UserDomainError("User with email: $email, not found")
