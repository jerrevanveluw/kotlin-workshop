package community.flock.workshop.domain.note.model

data class EnrichedNote(
    val note: Note,
    val user: String,
)
