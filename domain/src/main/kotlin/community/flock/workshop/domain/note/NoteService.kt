package community.flock.workshop.domain.note

import arrow.core.raise.either
import community.flock.workshop.domain.note.model.EnrichedNote
import community.flock.workshop.domain.user.UserContext
import community.flock.workshop.domain.user.UserService.getUserByEmail
import community.flock.workshop.domain.user.model.Email

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
