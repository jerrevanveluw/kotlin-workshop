package community.flock.workshop.domain.note

import community.flock.workshop.domain.note.model.Note

interface NoteAdapter {
    suspend fun getNotesByUserId(userId: String): List<Note>
}

interface HasNoteAdapter {
    val noteAdapter: NoteAdapter
}
