package community.flock.workshop.domain.user.model

import community.flock.workshop.domain.common.Value
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
        operator fun invoke(value: String) = value.takeIf { '@' in it }?.let(::Email)
    }
}

@JvmInline
value class FirstName private constructor(
    override val value: String,
) : Value<String> {
    override fun toString() = value

    companion object {
        operator fun invoke(value: String) = value.takeIf { it.isNotBlank() }?.let(::FirstName)
    }
}

@JvmInline
value class LastName private constructor(
    override val value: String,
) : Value<String> {
    override fun toString() = value

    companion object {
        operator fun invoke(value: String) = value.takeIf { it.isNotBlank() }?.let(::LastName)
    }
}

@JvmInline
value class BirthDate private constructor(
    override val value: LocalDate,
) : Value<LocalDate> {
    override fun toString() = value.toString()

    companion object {
        operator fun invoke(value: String) = runCatching { LocalDate.parse(value, ISO_LOCAL_DATE) }.map(::BirthDate).getOrNull()
    }
}
