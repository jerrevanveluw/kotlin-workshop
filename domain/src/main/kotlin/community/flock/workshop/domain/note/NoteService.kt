package community.flock.workshop.domain.note

import community.flock.workshop.domain.note.model.Note
import community.flock.workshop.domain.user.UserContext
import community.flock.workshop.domain.user.UserService.getUserById

interface NoteContext :
    HasNoteAdapter,
    UserContext

object NoteService {
    suspend fun NoteContext.getNoteByUserId(userId: String): List<Note> {
        val user = getUserById(userId)
        val notes = noteAdapter.getNotesByUserId(userId)
        return notes.map {
            it.copy(
                user = "${user.firstName} ${user.lastName}",
                email = user.email,
            )
        }
    }
}
