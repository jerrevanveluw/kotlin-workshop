package community.flock.workshop.app.note.upstream

import community.flock.workshop.api.note.NoteDto
import community.flock.workshop.app.note.upstream.NoteProducer.produce
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
        @RequestParam userId: String,
    ): List<NoteDto> =
        context
            .noteService
            .getNoteByUserId(userId)
            .map { it.produce() }
}
