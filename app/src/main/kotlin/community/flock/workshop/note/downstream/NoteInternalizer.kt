package community.flock.workshop.note.downstream

import community.flock.workshop.common.Internalizer
import community.flock.workshop.exception.DescriptionNotValid
import community.flock.workshop.exception.EmailNotValid
import community.flock.workshop.exception.NoteIdNotValid
import community.flock.workshop.exception.TitleNotValid
import community.flock.workshop.note.model.Description
import community.flock.workshop.note.model.Note
import community.flock.workshop.note.model.Title
import community.flock.workshop.user.model.Email

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
