package community.flock.workshop.app.user

import community.flock.workshop.api.generated.model.UserDto

object UserMother {
    const val USER_ID = "email@test.com"
    val user =
        UserDto(
            email = USER_ID,
            firstName = "FirstName",
            lastName = "LastName",
            birthDate = "2000-01-01",
        )
}
