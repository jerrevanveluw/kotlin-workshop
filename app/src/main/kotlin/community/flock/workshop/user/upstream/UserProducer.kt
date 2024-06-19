package community.flock.workshop.user.upstream

import community.flock.workshop.common.Producer
import community.flock.workshop.user.UserDto
import community.flock.workshop.user.model.User

object UserProducer : Producer<User, UserDto> {
    override fun User.produce() =
        UserDto(
            email = email,
            firstName = firstName,
            lastName = lastName,
            birthDate = birthDate,
        )
}
