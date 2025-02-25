package community.flock.workshop.app.note.upstream

import community.flock.workshop.api.note.NoteDto
import community.flock.workshop.app.common.Producer
import community.flock.workshop.domain.note.model.EnrichedNote

object NoteProducer : Producer<EnrichedNote, NoteDto> {
    override fun EnrichedNote.produce() =
        NoteDto(
            id = note.id,
            title = note.title,
            description = note.description,
            email = note.email,
            user = user,
            done = note.done,
        )
}
