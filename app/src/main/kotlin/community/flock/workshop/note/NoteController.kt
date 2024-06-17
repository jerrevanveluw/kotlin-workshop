package community.flock.workshop.note

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/notes")
class NoteController(
    private val noteService: NoteService,
) {
    @GetMapping
    suspend fun getNotesByUserId(
        @RequestParam userId: String,
    ): List<Note> = noteService.getNoteByUserId(userId)
}
