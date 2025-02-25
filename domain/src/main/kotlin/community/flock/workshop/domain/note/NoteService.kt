package community.flock.workshop.domain.note

import arrow.core.raise.either
import community.flock.workshop.domain.note.model.EnrichedNote
import community.flock.workshop.domain.user.HasUserService
import community.flock.workshop.domain.user.getUserByEmail
import community.flock.workshop.domain.user.model.Email

interface NoteService :
    HasNoteAdapter,
    HasUserService

suspend fun NoteService.getNoteByUserId(userId: Email) =
    either {
        val user = userService.getUserByEmail(userId).bind()
        val notes = noteAdapter.getNotesByUserId(userId).bind()
        notes.map {
            EnrichedNote(
                note = it,
                firstName = user.firstName,
                lastName = user.lastName,
            )
        }
    }

interface HasNoteService {
    val noteService: NoteService
}
