package community.flock.workshop.user

import community.flock.workshop.user.model.BirthDate
import community.flock.workshop.user.model.Email
import community.flock.workshop.user.model.FirstName
import community.flock.workshop.user.model.LastName
import community.flock.workshop.user.model.User

object UserMother {
    const val USER_ID = "email@test.com"
    val user =
        User(
            email = Email(USER_ID)!!,
            firstName = FirstName("firstName")!!,
            lastName = LastName("lastName")!!,
            birthDate = BirthDate("2000-01-01")!!,
        )
}
