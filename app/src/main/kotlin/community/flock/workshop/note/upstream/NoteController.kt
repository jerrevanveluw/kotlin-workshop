package community.flock.workshop.note.upstream

import community.flock.workshop.note.NoteAdapter
import community.flock.workshop.note.NoteContext
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
    noteAdapter: NoteAdapter,
    userRepository: LiveUserRepository,
) {
    private val context =
        object : NoteContext {
            override val noteAdapter = noteAdapter
            override val userRepository = userRepository
        }

    @GetMapping
    suspend fun getNotesByUserId(
        @RequestParam userId: String,
    ): List<ProducedNote> = context.getNoteByUserId(userId).map { it.produce() }
}
