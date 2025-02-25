package community.flock.workshop.domain.note

import community.flock.workshop.domain.note.model.EnrichedNote
import community.flock.workshop.domain.user.UserContext
import community.flock.workshop.domain.user.UserService.getUserById

interface NoteContext :
    HasNoteAdapter,
    UserContext

object NoteService {
    suspend fun NoteContext.getNoteByUserId(userId: String): List<EnrichedNote> {
        val user = getUserById(userId)
        val notes = noteAdapter.getNotesByUserId(userId)
        return notes.map {
            EnrichedNote(
                note = it,
                firstName = user.firstName,
                lastName = user.lastName,
            )
        }
    }
}
