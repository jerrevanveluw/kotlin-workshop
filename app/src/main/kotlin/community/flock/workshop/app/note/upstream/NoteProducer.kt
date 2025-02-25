package community.flock.workshop.app.note.upstream

import community.flock.workshop.api.note.NoteDto
import community.flock.workshop.app.common.Producer
import community.flock.workshop.app.note.upstream.NoteProducer.produce
import community.flock.workshop.domain.note.model.EnrichedNote

object NotesProducer : Producer<Iterable<EnrichedNote>, List<NoteDto>> {
    override fun Iterable<EnrichedNote>.produce() = map { it.produce() }
}

object NoteProducer : Producer<EnrichedNote, NoteDto> {
    override fun EnrichedNote.produce() =
        NoteDto(
            id = "${note.id}",
            title = "${note.title}",
            description = "${note.description}",
            email = "${note.email}",
            user = "$fullName",
            done = note.done,
        )
}
