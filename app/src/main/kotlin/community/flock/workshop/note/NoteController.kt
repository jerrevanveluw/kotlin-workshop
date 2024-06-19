package community.flock.workshop.note

import community.flock.workshop.note.NoteService.getNoteByUserId
import community.flock.workshop.note.model.Note
import community.flock.workshop.user.LiveUserRepository
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
    ): List<Note> = context.getNoteByUserId(userId)
}
