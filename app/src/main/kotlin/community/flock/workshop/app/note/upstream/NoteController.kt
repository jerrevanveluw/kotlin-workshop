package community.flock.workshop.app.note.upstream

import community.flock.wirespec.generated.model.NoteDto
import community.flock.workshop.app.common.handle
import community.flock.workshop.app.user.upstream.UserIdConsumer.validate
import community.flock.workshop.domain.note.HasNoteService
import community.flock.workshop.domain.note.getNoteByUserId
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

interface NoteControllerDI : HasNoteService

@RestController
@RequestMapping("/api/notes")
class NoteController(
    private val context: NoteControllerDI,
) {
    @GetMapping
    suspend fun getNotesByUserId(
        @RequestParam potentialUserId: String,
    ): List<NoteDto> =
        handle(NotesProducer) {
            val id = potentialUserId.validate().bind()
            context.noteService
                .getNoteByUserId(id)
                .bind()
        }
}
