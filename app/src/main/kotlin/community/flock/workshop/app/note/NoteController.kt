package community.flock.workshop.app.note

import community.flock.workshop.api.note.NoteDto
import community.flock.workshop.domain.note.HasNoteService
import community.flock.workshop.domain.note.getNoteByUserId
import community.flock.workshop.domain.note.model.Note
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

interface NoteControllerDI : HasNoteService

@RestController
@RequestMapping("/api/notes")
class NoteController(
    context: NoteControllerDI,
) : NoteControllerDI by context {
    @GetMapping
    suspend fun getNotesByUserId(
        @RequestParam userId: String,
    ): List<NoteDto> =
        noteService
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
