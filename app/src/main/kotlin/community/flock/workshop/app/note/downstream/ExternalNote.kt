package community.flock.workshop.app.note.downstream

data class ExternalNote(
    val id: String,
    val title: String,
    val description: String,
    val email: String,
    val done: Boolean,
)
