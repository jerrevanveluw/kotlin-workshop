package community.flock.workshop.app.note.downstream

import community.flock.workshop.app.common.Internalizer
import community.flock.workshop.app.exception.DescriptionNotValid
import community.flock.workshop.app.exception.EmailNotValid
import community.flock.workshop.app.exception.NoteIdNotValid
import community.flock.workshop.app.exception.TitleNotValid
import community.flock.workshop.domain.note.model.Description
import community.flock.workshop.domain.note.model.Note
import community.flock.workshop.domain.note.model.Title
import community.flock.workshop.domain.user.model.Email

object NoteInternalizer : Internalizer<ExternalNote, Note> {
    override fun ExternalNote.internalize() =
        run {
            val id = Note.Id(id) ?: throw NoteIdNotValid()
            val title = Title(title) ?: throw TitleNotValid()
            val description = Description(description) ?: throw DescriptionNotValid()
            val email = Email(email) ?: throw EmailNotValid()
            Note(
                id = id,
                title = title,
                description = description,
                email = email,
                done = done,
            )
        }
}
