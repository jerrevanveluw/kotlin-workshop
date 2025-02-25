package community.flock.workshop.domain.error

import community.flock.workshop.domain.user.model.Email

sealed class UserDomainError(
    message: String,
) : DomainError(message)

class UserNotFound(
    email: Email,
) : UserDomainError("User with email: $email, not found")
