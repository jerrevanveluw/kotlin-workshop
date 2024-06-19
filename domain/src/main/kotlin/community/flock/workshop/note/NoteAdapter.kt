package community.flock.workshop.note

import community.flock.workshop.note.model.Note

interface NoteAdapter {
    suspend fun getNotesByUserId(userId: String): List<Note>
}

interface HasNoteAdapter {
    val noteAdapter: NoteAdapter
}
