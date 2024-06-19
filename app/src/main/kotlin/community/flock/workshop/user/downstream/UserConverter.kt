package community.flock.workshop.user.downstream

import community.flock.workshop.common.Converter
import community.flock.workshop.user.model.User

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
