package community.flock.workshop.api.note

data class NoteDto(
    val id: String,
    val title: String,
    val description: String,
    val email: String,
    val user: String?,
    val done: Boolean,
)
