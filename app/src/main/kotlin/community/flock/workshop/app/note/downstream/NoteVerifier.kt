package community.flock.workshop.app.note.downstream

import arrow.core.raise.either
import arrow.core.raise.zipOrAccumulate
import community.flock.workshop.app.common.Verifier
import community.flock.workshop.domain.error.ValidationError
import community.flock.workshop.domain.note.model.Description
import community.flock.workshop.domain.note.model.Note
import community.flock.workshop.domain.note.model.Title
import community.flock.workshop.domain.user.model.Email

object NoteVerifier : Verifier<ValidationError, ExternalNote, Note> {
    override fun ExternalNote.internalize() =
        either {
            zipOrAccumulate(
                { Note.Id(id).bind() },
                { Title(title).bind() },
                { Description(description).bind() },
                { Email(email).bind() },
                { done },
                ::Note,
            )
        }
}
