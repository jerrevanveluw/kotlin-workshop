package community.flock.workshop.domain.note.model

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import community.flock.workshop.domain.common.Value
import community.flock.workshop.domain.error.DescriptionError
import community.flock.workshop.domain.error.NoteIdError
import community.flock.workshop.domain.error.TitleError
import community.flock.workshop.domain.user.model.Email
import java.util.UUID

data class Note(
    val id: Id,
    val title: Title,
    val description: Description,
    val email: Email,
    val done: Boolean,
) {
    @JvmInline
    value class Id(
        override val value: UUID,
    ) : Value<UUID> {
        override fun toString() = value.toString()

        companion object {
            operator fun invoke(value: String) =
                Either
                    .catch { UUID.fromString(value) }
                    .mapLeft { NoteIdError.InvalidUUID }
                    .map(::Id)
        }
    }
}

@JvmInline
value class Title private constructor(
    override val value: String,
) : Value<String> {
    override fun toString() = value

    companion object {
        operator fun invoke(value: String) =
            either {
                ensure(value.isNotBlank()) { TitleError.Empty }
                Title(value)
            }
    }
}

@JvmInline
value class Description private constructor(
    override val value: String,
) : Value<String> {
    override fun toString() = value

    companion object {
        operator fun invoke(value: String) =
            either {
                ensure(value.isNotBlank()) { DescriptionError.Empty }
                Description(value)
            }
    }
}
