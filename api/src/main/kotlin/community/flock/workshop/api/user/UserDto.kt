package community.flock.workshop.api.user

data class UserDto(
    val email: String,
    val firstName: String,
    val lastName: String,
    val birthDate: String,
)
