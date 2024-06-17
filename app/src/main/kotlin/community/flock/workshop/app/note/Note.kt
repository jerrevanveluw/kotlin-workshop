package community.flock.workshop.app.note

import community.flock.workshop.api.note.NoteDto
import community.flock.workshop.app.common.Producible

data class Note(
    val id: String,
    val title: String,
    val description: String,
    val email: String,
    val user: String?,
    val done: Boolean,
) : Producible<NoteDto> {
    override fun produce(): NoteDto =
        NoteDto(
            id = id,
            title = title,
            description = description,
            email = email,
            user = user,
            done = done,
        )
}
