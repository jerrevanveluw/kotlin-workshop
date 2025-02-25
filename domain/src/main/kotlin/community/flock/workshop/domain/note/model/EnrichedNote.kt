package community.flock.workshop.domain.note.model

import community.flock.workshop.domain.common.Value
import community.flock.workshop.domain.user.model.FirstName
import community.flock.workshop.domain.user.model.LastName

data class EnrichedNote(
    val note: Note,
    val fullName: FullName,
) {
    constructor(note: Note, firstName: FirstName, lastName: LastName) : this(
        note = note,
        fullName = firstName + lastName,
    )
}

operator fun FirstName.plus(lastName: LastName) = FullName("$value ${lastName.value}")

@JvmInline
value class FullName(
    override val value: String,
) : Value<String> {
    override fun toString() = value

    companion object {
        operator fun invoke(value: String) = FullName(value)
    }
}
