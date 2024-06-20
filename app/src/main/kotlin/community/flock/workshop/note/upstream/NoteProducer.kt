package community.flock.workshop.note.upstream

import community.flock.workshop.common.Producer
import community.flock.workshop.note.model.EnrichedNote

object NoteProducer : Producer<EnrichedNote, ProducedNote> {
    override fun EnrichedNote.produce() =
        ProducedNote(
            id = "${note.id}",
            title = "${note.title}",
            description = "${note.description}",
            email = "${note.email}",
            user = "$fullName",
            done = note.done,
        )
}
