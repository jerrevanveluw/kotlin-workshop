package community.flock.workshop.app.note.upstream

import community.flock.workshop.api.generated.endpoint.GetNotesByUserId
import community.flock.workshop.app.common.wirespec
import community.flock.workshop.app.user.upstream.UserIdConsumer.validate
import community.flock.workshop.domain.note.HasNoteService
import community.flock.workshop.domain.note.getNoteByUserId
import org.springframework.web.bind.annotation.RestController

interface NoteControllerDI : HasNoteService

interface UpstreamNotesApi : GetNotesByUserId.Handler

@RestController
class NoteController(
    private val context: NoteControllerDI,
) : UpstreamNotesApi {
    override suspend fun getNotesByUserId(request: GetNotesByUserId.Request): GetNotesByUserId.Response<*> =
        wirespec(NotesProducer, GetNotesByUserId::Response200) {
            val id =
                request.queries.potentialUserId
                    .validate()
                    .bind()
            context.noteService.getNoteByUserId(id).bind()
        }
}
