package community.flock.workshop.app.note.upstream

import community.flock.workshop.api.generated.GetNotesByUserIdEndpoint
import community.flock.workshop.app.common.wirespec
import community.flock.workshop.app.user.upstream.UserIdConsumer.validate
import community.flock.workshop.domain.note.NoteAdapter
import community.flock.workshop.domain.note.NoteContext
import community.flock.workshop.domain.note.NoteService.getNoteByUserId
import community.flock.workshop.domain.user.UserRepository
import org.springframework.web.bind.annotation.RestController

interface UpstreamNotesApi : GetNotesByUserIdEndpoint.Handler

@RestController
class NoteController(
    noteAdapter: NoteAdapter,
    userRepository: UserRepository,
) : UpstreamNotesApi {
    private val context =
        object : NoteContext {
            override val noteAdapter = noteAdapter
            override val userRepository = userRepository
        }

    override suspend fun getNotesByUserId(request: GetNotesByUserIdEndpoint.Request): GetNotesByUserIdEndpoint.Response<*> =
        wirespec(NotesProducer, GetNotesByUserIdEndpoint::Response200) {
            val id =
                request.queries.potentialUserId
                    .validate()
                    .bind()
            context.getNoteByUserId(id).bind()
        }
}
