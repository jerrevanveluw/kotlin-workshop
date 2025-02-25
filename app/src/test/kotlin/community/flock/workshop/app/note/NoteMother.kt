package community.flock.workshop.app.note

import community.flock.workshop.app.user.UserMother.USER_ID
import community.flock.workshop.domain.note.model.Description
import community.flock.workshop.domain.note.model.Note
import community.flock.workshop.domain.note.model.Title
import community.flock.workshop.domain.user.model.Email
import io.kotest.assertions.arrow.core.shouldBeRight

object NoteMother {
    val note =
        Note(
            id = Note.Id("0ec33ba3-64a1-4f34-a33d-6cab4b43baeb").shouldBeRight(),
            title = Title("title").shouldBeRight(),
            description = Description("description").shouldBeRight(),
            email = Email(USER_ID).shouldBeRight(),
            done = true,
        )
}
