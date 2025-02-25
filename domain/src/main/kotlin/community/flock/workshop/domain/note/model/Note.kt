package community.flock.workshop.domain.note.model

import community.flock.workshop.domain.common.Value
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
            operator fun invoke(value: String) = value.runCatching(UUID::fromString).map(::Id).getOrNull()
        }
    }
}

@JvmInline
value class Title private constructor(
    override val value: String,
) : Value<String> {
    override fun toString() = value

    companion object {
        operator fun invoke(value: String) = value.takeIf { it.isNotBlank() }?.let(::Title)
    }
}

@JvmInline
value class Description private constructor(
    override val value: String,
) : Value<String> {
    override fun toString() = value

    companion object {
        operator fun invoke(value: String) = value.takeIf { it.isNotBlank() }?.let(::Description)
    }
}
