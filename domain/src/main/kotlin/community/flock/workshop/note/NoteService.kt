package community.flock.workshop.note

import arrow.core.raise.either
import community.flock.workshop.note.model.EnrichedNote
import community.flock.workshop.user.UserContext
import community.flock.workshop.user.UserService.getUserByEmail
import community.flock.workshop.user.model.Email

interface NoteContext :
    HasNoteAdapter,
    UserContext

object NoteService {
    suspend fun NoteContext.getNoteByUserId(userId: Email) =
        either {
            val user = getUserByEmail(userId).bind()
            val notes = noteAdapter.getNotesByUserId(userId).bind()
            notes.map {
                EnrichedNote(
                    note = it,
                    firstName = user.firstName,
                    lastName = user.lastName,
                )
            }
        }
}
