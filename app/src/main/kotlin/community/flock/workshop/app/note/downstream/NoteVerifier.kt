package community.flock.workshop.app.note.downstream

import arrow.core.raise.either
import arrow.core.raise.zipOrAccumulate
import community.flock.wirespec.kotlin.Wirespec
import community.flock.workshop.app.common.Internalizer
import community.flock.workshop.app.common.Verifier
import community.flock.workshop.app.note.downstream.WirespecRefinedInternalizer.internalize
import community.flock.workshop.domain.error.ValidationError
import community.flock.workshop.domain.note.model.Description
import community.flock.workshop.domain.note.model.Note
import community.flock.workshop.domain.note.model.Title
import community.flock.workshop.domain.user.model.Email
import community.flock.workshop.spi.generated.model.NoteDto as ExternalNote

object NoteVerifier : Verifier<ValidationError, ExternalNote, Note> {
    override fun ExternalNote.internalize() =
        either {
            zipOrAccumulate(
                { Note.Id(id.internalize()).bind() },
                { Title(title).bind() },
                { Description(description).bind() },
                { Email(email.internalize()).bind() },
                { done },
                ::Note,
            )
        }
}

private object WirespecRefinedInternalizer : Internalizer<Wirespec.Refined, String> {
    override fun Wirespec.Refined.internalize() = value
}
