package community.flock.workshop.domain.note.model

data class Note(
    val id: String,
    val title: String,
    val description: String,
    val email: String,
    val user: String?,
    val done: Boolean,
)
