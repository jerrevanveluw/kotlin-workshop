package community.flock.workshop.note.downstream

import community.flock.workshop.common.Internalizer
import community.flock.workshop.note.model.Note

object NoteInternalizer : Internalizer<ExternalNote, Note> {
    override fun ExternalNote.internalize() =
        Note(
            id = id,
            title = title,
            description = description,
            email = email,
            done = done,
        )
}
