package community.flock.workshop.note.downstream

import arrow.core.Either
import arrow.core.raise.either
import community.flock.workshop.common.Internalizer
import community.flock.workshop.error.ValidationError
import community.flock.workshop.note.model.Description
import community.flock.workshop.note.model.Note
import community.flock.workshop.note.model.Title
import community.flock.workshop.user.model.Email

object NoteInternalizer : Internalizer<ExternalNote, Either<ValidationError, Note>> {
    override fun ExternalNote.internalize() =
        either {
            Note(
                id = Note.Id(id).bind(),
                title = Title(title).bind(),
                description = Description(description).bind(),
                email = Email(email).bind(),
                done = done,
            )
        }
}
