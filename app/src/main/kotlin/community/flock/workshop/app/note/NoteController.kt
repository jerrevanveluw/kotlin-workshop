package community.flock.workshop.app.note

import community.flock.workshop.api.note.NoteDto
import community.flock.workshop.domain.note.NoteAdapter
import community.flock.workshop.domain.note.NoteContext
import community.flock.workshop.domain.note.NoteService.getNoteByUserId
import community.flock.workshop.domain.note.model.Note
import community.flock.workshop.domain.user.UserRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/notes")
class NoteController(
    liveNoteAdapter: NoteAdapter,
    liveUserRepository: UserRepository,
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

private fun Note.produce() =
    NoteDto(
        id = id,
        title = title,
        description = description,
        email = email,
        user = user,
        done = done,
    )
