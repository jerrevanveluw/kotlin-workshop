package community.flock.workshop.domain.user.model

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import community.flock.workshop.domain.common.Value
import community.flock.workshop.domain.error.BirthDayError
import community.flock.workshop.domain.error.EmailValidationError
import community.flock.workshop.domain.error.FirstNameError
import community.flock.workshop.domain.error.LastNameError
import java.time.LocalDate
import java.time.format.DateTimeFormatter.ISO_LOCAL_DATE

data class User(
    val email: Email,
    val firstName: FirstName,
    val lastName: LastName,
    val birthDate: BirthDate,
)

@JvmInline
value class Email private constructor(
    override val value: String,
) : Value<String> {
    override fun toString() = value

    companion object {
        operator fun invoke(value: String) =
            either {
                ensure('@' in value) { EmailValidationError.Invalid }
                Email(value)
            }
    }
}

@JvmInline
value class FirstName private constructor(
    override val value: String,
) : Value<String> {
    override fun toString() = value

    companion object {
        operator fun invoke(value: String) =
            either {
                ensure(value.isNotBlank()) { FirstNameError.Empty }
                FirstName(value.trim())
            }
    }
}

@JvmInline
value class LastName private constructor(
    override val value: String,
) : Value<String> {
    override fun toString() = value

    companion object {
        operator fun invoke(value: String) =
            either {
                ensure(value.isNotBlank()) { LastNameError.Empty }
                LastName(value.trim())
            }
    }
}

@JvmInline
value class BirthDate private constructor(
    override val value: LocalDate,
) : Value<LocalDate> {
    override fun toString() = value.toString()

    companion object {
        operator fun invoke(value: String) =
            Either
                .catch { LocalDate.parse(value, ISO_LOCAL_DATE) }
                .mapLeft { BirthDayError.Invalid }
                .map(::BirthDate)
    }
}
