package community.flock.workshop.app.exception

sealed class AppException(
    override val message: String,
) : RuntimeException(message)

sealed class ValidationException(
    what: String,
) : AppException("$what is not valid")

sealed class UserValidationException(
    what: String,
) : ValidationException(what)

class EmailNotValid : UserValidationException("Email")

class FirstNameNotValid : UserValidationException("FirstName")

class LastNameNotValid : UserValidationException("LastName")

class BirthDateNotValid : UserValidationException("BirthDate")

sealed class NoteValidationException(
    what: String,
) : ValidationException(what)

class NoteIdNotValid : NoteValidationException("NoteId")

class TitleNotValid : NoteValidationException("Title")

class DescriptionNotValid : NoteValidationException("Description")
