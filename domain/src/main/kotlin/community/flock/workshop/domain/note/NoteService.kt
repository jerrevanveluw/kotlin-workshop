package community.flock.workshop.domain.note

import community.flock.workshop.domain.note.model.EnrichedNote
import community.flock.workshop.domain.user.HasUserService
import community.flock.workshop.domain.user.getUserById

interface NoteService :
    HasNoteAdapter,
    HasUserService

suspend fun NoteService.getNoteByUserId(userId: String): List<EnrichedNote> {
    val user = userService.getUserById(userId)
    val notes = noteAdapter.getNotesByUserId(userId)
    return notes.map {
        EnrichedNote(
            note = it,
            user = "${user.firstName} ${user.lastName}",
        )
    }
}

interface HasNoteService {
    val noteService: NoteService
}
