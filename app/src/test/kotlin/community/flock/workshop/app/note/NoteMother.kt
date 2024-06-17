package community.flock.workshop.app.note

import community.flock.workshop.app.user.UserMother.USER_ID

object NoteMother {
    val note =
        Note(
            id = "id",
            title = "title",
            description = "description",
            email = USER_ID,
            user = null,
            done = true,
        )
}
