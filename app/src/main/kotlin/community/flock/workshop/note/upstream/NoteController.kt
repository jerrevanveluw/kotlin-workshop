package community.flock.workshop.note.upstream

import arrow.core.raise.either
import community.flock.workshop.common.handle
import community.flock.workshop.common.mapError
import community.flock.workshop.note.NoteAdapter
import community.flock.workshop.note.NoteContext
import community.flock.workshop.note.NoteService.getNoteByUserId
import community.flock.workshop.note.upstream.NoteProducer.produce
import community.flock.workshop.user.downstream.LiveUserRepository
import community.flock.workshop.user.model.Email
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
    ): List<ProducedNote> =
        either {
            val email = Email(userId).mapError().bind()
            context
                .getNoteByUserId(email)
                .mapError()
                .bind()
                .map { it.produce() }
        }.handle()
}
