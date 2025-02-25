package community.flock.workshop.app.note.downstream

import arrow.core.Either
import arrow.core.raise.either
import community.flock.workshop.app.common.catch
import community.flock.workshop.app.note.downstream.NoteVerifier.verify
import community.flock.workshop.domain.error.Error
import community.flock.workshop.domain.note.NoteAdapter
import community.flock.workshop.domain.note.model.Note
import community.flock.workshop.domain.user.model.Email
import kotlinx.coroutines.flow.map
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
    override suspend fun getNotesByUserId(email: Email): Either<Error, List<Note>> =
        either {
            notesClient
                .get()
                .uri("/$email")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux<ExternalNote>()
                .catch { asFlow() }
                .bind()
                .map { it.verify().bind() }
                .toList()
        }
}
