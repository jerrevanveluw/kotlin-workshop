package community.flock.workshop.app.user.upstream

import community.flock.workshop.api.user.UserDto
import community.flock.workshop.app.common.Producer
import community.flock.workshop.app.common.Transformer
import community.flock.workshop.app.user.upstream.UserTransformer.produce
import community.flock.workshop.domain.user.model.User

object UsersProducer : Producer<Iterable<User>, List<UserDto>> {
    override fun Iterable<User>.produce() = map { it.produce() }
}

object UserTransformer : Transformer<User, UserDto> {
    override fun User.produce() =
        UserDto(
            email = email,
            firstName = firstName,
            lastName = lastName,
            birthDate = birthDate,
        )

    override fun UserDto.consume() =
        User(
            email = email,
            firstName = firstName,
            lastName = lastName,
            birthDate = birthDate,
        )
}
