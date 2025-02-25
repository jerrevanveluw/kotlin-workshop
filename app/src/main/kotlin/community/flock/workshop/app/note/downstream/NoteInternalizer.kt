package community.flock.workshop.app.note.downstream

import arrow.core.Either
import arrow.core.raise.either
import community.flock.workshop.app.common.Internalizer
import community.flock.workshop.domain.error.ValidationError
import community.flock.workshop.domain.note.model.Description
import community.flock.workshop.domain.note.model.Note
import community.flock.workshop.domain.note.model.Title
import community.flock.workshop.domain.user.model.Email

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
