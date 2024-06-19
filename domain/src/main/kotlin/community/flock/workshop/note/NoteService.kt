package community.flock.workshop.note

import community.flock.workshop.note.model.EnrichedNote
import community.flock.workshop.user.UserContext
import community.flock.workshop.user.UserService.getUserById

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
                user = "${user.firstName} ${user.lastName}",
            )
        }
    }
}
