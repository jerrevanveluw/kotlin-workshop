package community.flock.workshop.app.note

import community.flock.workshop.domain.note.NoteAdapter
import community.flock.workshop.domain.note.model.Note
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.asFlow
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToFlux

@Component
class LiveNoteAdapter(
    private val notesClient: WebClient,
) : NoteAdapter {
    override suspend fun getNotesByUserId(userId: String) =
        notesClient
            .get()
            .uri("/$userId")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToFlux<Note>()
            .asFlow()
            .toList()
}
