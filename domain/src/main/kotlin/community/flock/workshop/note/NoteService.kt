package community.flock.workshop.note

import community.flock.workshop.note.model.Note
import community.flock.workshop.user.UserContext
import community.flock.workshop.user.UserService.getUserById

interface NoteContext : HasNoteAdapter, UserContext

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
