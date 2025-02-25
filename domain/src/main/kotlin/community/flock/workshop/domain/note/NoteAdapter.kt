package community.flock.workshop.domain.note

import arrow.core.Either
import community.flock.workshop.domain.error.Error
import community.flock.workshop.domain.note.model.Note
import community.flock.workshop.domain.user.model.Email

interface NoteAdapter {
    suspend fun getNotesByUserId(email: Email): Either<Error, List<Note>>
}

interface HasNoteAdapter {
    val noteAdapter: NoteAdapter
}
