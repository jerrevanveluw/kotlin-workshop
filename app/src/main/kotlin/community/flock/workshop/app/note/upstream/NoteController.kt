package community.flock.workshop.app.note.upstream

import arrow.core.raise.either
import community.flock.workshop.api.note.NoteDto
import community.flock.workshop.app.common.handle
import community.flock.workshop.app.common.mapError
import community.flock.workshop.app.note.upstream.NoteProducer.produce
import community.flock.workshop.domain.note.NoteAdapter
import community.flock.workshop.domain.note.NoteContext
import community.flock.workshop.domain.note.NoteService.getNoteByUserId
import community.flock.workshop.domain.user.UserRepository
import community.flock.workshop.domain.user.model.Email
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/notes")
class NoteController(
    noteAdapter: NoteAdapter,
    userRepository: UserRepository,
) {
    private val context =
        object : NoteContext {
            override val noteAdapter = noteAdapter
            override val userRepository = userRepository
        }

    @GetMapping
    suspend fun getNotesByUserId(
        @RequestParam userId: String,
    ): List<NoteDto> =
        either {
            val email = Email(userId).mapError().bind()
            context
                .getNoteByUserId(email)
                .mapError()
                .bind()
                .map { it.produce() }
        }.handle()
}
