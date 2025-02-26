type UUID = String(/^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$/g)
type Email = String(/^[^@]+@[^@]+\.[^@]+$/g)

type NoteDto {
    id: UUID,
    title: String,
    description: String,
    email: Email,
    done: Boolean
}

endpoint GetNotes GET /api/notes -> {
    200 -> NoteDto[]
}

endpoint GetNotesByEmail GET /api/notes/{email: String} -> {
    200 -> NoteDto[]
}
