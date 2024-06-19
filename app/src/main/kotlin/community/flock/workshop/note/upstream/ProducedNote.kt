package community.flock.workshop.note.upstream

data class ProducedNote(
    val id: String,
    val title: String,
    val description: String,
    val email: String,
    val user: String,
    val done: Boolean,
)
