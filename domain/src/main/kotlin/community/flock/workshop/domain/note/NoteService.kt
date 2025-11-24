package community.flock.workshop.domain.note

import community.flock.workshop.domain.note.model.Note
import community.flock.workshop.domain.user.HasUserService
import community.flock.workshop.domain.user.getUserById

interface NoteService :
    HasNoteAdapter,
    HasUserService

suspend fun NoteService.getNoteByUserId(userId: String): List<Note> {
    val user = userService.getUserById(userId)
    val notes = noteAdapter.getNotesByUserId(userId)
    return notes.map {
        it.copy(
            user = "${user.firstName} ${user.lastName}",
            email = user.email,
        )
    }
}

interface HasNoteService {
    val noteService: NoteService
}
