package community.flock.workshop.note

import community.flock.workshop.common.Producible

data class Note(
    val id: String,
    val title: String,
    val description: String,
    val email: String,
    val user: String?,
    val done: Boolean,
) : Producible<NoteDto> {
    override fun toDto() =
        NoteDto(
            id = id,
            title = title,
            description = description,
            email = email,
            user = user,
            done = done,
        )
}
