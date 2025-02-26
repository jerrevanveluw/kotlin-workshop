type NoteDto {
    id: String,
    title: String,
    description: String,
    email: String,
    user: String?,
    done: Boolean
}

endpoint GetNotesByUserId GET /api/notes?{potentialUserId: String} -> {
    200 -> NoteDto[]
}
