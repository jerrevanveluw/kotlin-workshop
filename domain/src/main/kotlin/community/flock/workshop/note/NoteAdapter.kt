package community.flock.workshop.note

import arrow.core.Either
import community.flock.workshop.error.ValidationError
import community.flock.workshop.note.model.Note
import community.flock.workshop.user.model.Email

interface NoteAdapter {
    suspend fun getNotesByUserId(email: Email): Either<ValidationError, List<Note>>
}

interface HasNoteAdapter {
    val noteAdapter: NoteAdapter
}
