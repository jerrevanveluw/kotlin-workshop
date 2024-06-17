package community.flock.workshop.app.user

import community.flock.workshop.api.user.UserDto

object UserMother {
    const val USER_ID = "email@test.com"
    val user =
        UserDto(
            email = USER_ID,
            firstName = "firstName",
            lastName = "lastName",
            birthDate = "2000-01-01",
        )
}
