package community.flock.workshop.app.note.downstream

import community.flock.workshop.app.common.Internalizer
import community.flock.workshop.domain.note.model.Note

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
