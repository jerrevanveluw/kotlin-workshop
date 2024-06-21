package community.flock.workshop.note

import community.flock.workshop.note.model.Description
import community.flock.workshop.note.model.Note
import community.flock.workshop.note.model.Title
import community.flock.workshop.user.UserMother.USER_ID
import community.flock.workshop.user.model.Email

object NoteMother {
    val note =
        Note(
            id = Note.Id("0ec33ba3-64a1-4f34-a33d-6cab4b43baeb").getOrNull()!!,
            title = Title("title").getOrNull()!!,
            description = Description("description").getOrNull()!!,
            email = Email(USER_ID).getOrNull()!!,
            done = true,
        )
}
