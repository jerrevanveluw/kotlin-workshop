package community.flock.workshop.domain.error

sealed class NoteValidationError(
    message: String,
) : ValidationError(message)

sealed class NoteIdError(
    message: String,
) : NoteValidationError(message) {
    data object InvalidUUID : NoteIdError("Id not a valid UUID")
}

sealed class TitleError(
    message: String,
) : NoteValidationError(message) {
    data object Empty : TitleError("Title cannot be empty")
}

sealed class DescriptionError(
    message: String,
) : NoteValidationError(message) {
    data object Empty : DescriptionError("Description cannot be empty")
}
