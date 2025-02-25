package community.flock.workshop.app.note

import community.flock.workshop.app.user.UserMother.USER_ID
import community.flock.workshop.domain.note.model.Description
import community.flock.workshop.domain.note.model.Note
import community.flock.workshop.domain.note.model.Title
import community.flock.workshop.domain.user.model.Email

object NoteMother {
    val note =
        Note(
            id = Note.Id("0ec33ba3-64a1-4f34-a33d-6cab4b43baeb")!!,
            title = Title("title")!!,
            description = Description("description")!!,
            email = Email(USER_ID)!!,
            done = true,
        )
}
