package community.flock.workshop.user

object UserMother {
    const val USER_ID = "email@test.com"
    val user =
        User(
            email = USER_ID,
            firstName = "firstName",
            lastName = "lastName",
            birthDate = "birthDate",
        )
}
