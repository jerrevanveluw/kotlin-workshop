package community.flock.workshop.domain.error

sealed class UserValidationError(
    message: String,
) : ValidationError(message)

sealed class EmailValidationError(
    message: String,
) : UserValidationError(message) {
    data object Empty : EmailValidationError("Email cannot be empty")

    data object Invalid : EmailValidationError("Email is invalid")
}

sealed class FirstNameError(
    message: String,
) : UserValidationError(message) {
    data object Empty : FirstNameError("First name cannot be empty")

    data object NotCapitalized : FirstNameError("First character in first name must be capitalized")
}

sealed class LastNameError(
    message: String,
) : UserValidationError(message) {
    data object Empty : LastNameError("Last name cannot be empty")

    data object NotCapitalized : LastNameError("First character in last name must be capitalized")
}

sealed class BirthDayError(
    message: String,
) : UserValidationError(message) {
    data object Invalid : BirthDayError("Birthday is invalid")
}
