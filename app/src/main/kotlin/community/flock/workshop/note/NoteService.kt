package community.flock.workshop.note

import community.flock.workshop.exception.UserNotFoundException
import community.flock.workshop.user.UserService
import org.springframework.stereotype.Service

@Service
class NoteService(
    private val noteAdapter: NoteAdapter,
    private val userService: UserService,
) {
    suspend fun getNoteByUserId(userId: String): List<Note> {
        val user = userService.getUserById(userId) ?: throw UserNotFoundException()
        val notes = noteAdapter.getNotesByUserId(userId)
        return notes.map {
            it.copy(
                user = "${user.firstName} ${user.lastName}",
                email = user.email,
            )
        }
    }
}
