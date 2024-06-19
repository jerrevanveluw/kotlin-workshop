package community.flock.workshop.note.upstream

import community.flock.workshop.note.NoteAdapter
import community.flock.workshop.note.NoteContext
import community.flock.workshop.note.NoteDto
import community.flock.workshop.note.NoteService.getNoteByUserId
import community.flock.workshop.note.upstream.NoteProducer.produce
import community.flock.workshop.user.downstream.LiveUserRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/notes")
class NoteController(
    liveNoteAdapter: NoteAdapter,
    liveUserRepository: LiveUserRepository,
) {
    private val context =
        object : NoteContext {
            override val noteAdapter = liveNoteAdapter
            override val userRepository = liveUserRepository
        }

    @GetMapping
    suspend fun getNotesByUserId(
        @RequestParam userId: String,
    ): List<NoteDto> =
        context
            .getNoteByUserId(userId)
            .map { it.produce() }
}
