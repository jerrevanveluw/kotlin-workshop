package community.flock.workshop.app.common

import community.flock.workshop.app.note.downstream.LiveNoteAdapter
import community.flock.workshop.app.note.upstream.NoteControllerDI
import community.flock.workshop.app.user.downstream.LiveUserRepository
import community.flock.workshop.app.user.upstream.UserControllerDI
import community.flock.workshop.domain.note.NoteService
import community.flock.workshop.domain.user.UserService
import org.springframework.stereotype.Component

interface AppContext :
    UserControllerDI,
    NoteControllerDI

@Component
class LiveAppContext(
    liveUserRepository: LiveUserRepository,
    liveNoteAdapter: LiveNoteAdapter,
) : AppContext {
    override val userService =
        object : UserService {
            override val userRepository = liveUserRepository
        }

    override val noteService =
        object : NoteService {
            override val noteAdapter = liveNoteAdapter
            override val userService = this@LiveAppContext.userService
        }
}
