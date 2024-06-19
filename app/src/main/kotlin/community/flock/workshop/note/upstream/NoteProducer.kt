package community.flock.workshop.note.upstream

import community.flock.workshop.common.Producer
import community.flock.workshop.note.NoteDto
import community.flock.workshop.note.model.EnrichedNote

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
