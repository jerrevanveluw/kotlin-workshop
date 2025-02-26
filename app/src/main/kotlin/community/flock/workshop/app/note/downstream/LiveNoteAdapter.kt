package community.flock.workshop.app.note.downstream

import arrow.core.Either
import arrow.core.raise.either
import community.flock.wirespec.integration.spring.kotlin.client.WirespecWebClient
import community.flock.workshop.app.note.downstream.NoteVerifier.verify
import community.flock.workshop.domain.error.Error
import community.flock.workshop.domain.note.NoteAdapter
import community.flock.workshop.domain.note.model.Note
import community.flock.workshop.domain.user.model.Email
import community.flock.workshop.spi.generated.endpoint.GetNotesByEmail
import org.springframework.stereotype.Component

interface DownstreamNotesApi : GetNotesByEmail.Handler

@Component
class NoteWebClient(
    private val wirespecWebClient: WirespecWebClient,
) : DownstreamNotesApi {
    override suspend fun getNotesByEmail(request: GetNotesByEmail.Request): GetNotesByEmail.Response<*> = wirespecWebClient.send(request)
}

@Component
class LiveNoteAdapter(
    private val client: NoteWebClient,
) : NoteAdapter {
    override suspend fun getNotesByUserId(email: Email): Either<Error, List<Note>> =
        either {
            val req = GetNotesByEmail.Request(email = email.value)
            when (val res = client.getNotesByEmail(req)) {
                is GetNotesByEmail.Response200 -> res.body.map { it.verify().bind() }
            }
        }
}
