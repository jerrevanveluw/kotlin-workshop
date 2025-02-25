package community.flock.workshop.app.user.downstream

import community.flock.workshop.app.common.Converter
import community.flock.workshop.domain.user.model.User

object UserConverter : Converter<User, UserEntity> {
    override fun User.externalize() =
        UserEntity(
            email = email,
            firstName = firstName,
            lastName = lastName,
            birthDate = birthDate,
        )

    override fun UserEntity.internalize() =
        User(
            email = email,
            firstName = firstName,
            lastName = lastName,
            birthDate = birthDate,
        )
}
