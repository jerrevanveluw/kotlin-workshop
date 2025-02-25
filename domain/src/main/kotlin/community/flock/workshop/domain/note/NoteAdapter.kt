package community.flock.workshop.domain.note

import arrow.core.Either
import community.flock.workshop.domain.error.ValidationError
import community.flock.workshop.domain.note.model.Note
import community.flock.workshop.domain.user.model.Email

interface NoteAdapter {
    suspend fun getNotesByUserId(email: Email): Either<ValidationError, List<Note>>
}

interface HasNoteAdapter {
    val noteAdapter: NoteAdapter
}
