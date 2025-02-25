package community.flock.workshop.app.user.downstream

import arrow.core.raise.either
import arrow.core.raise.zipOrAccumulate
import community.flock.workshop.app.common.Converter
import community.flock.workshop.domain.error.UserValidationError
import community.flock.workshop.domain.user.model.BirthDate
import community.flock.workshop.domain.user.model.Email
import community.flock.workshop.domain.user.model.FirstName
import community.flock.workshop.domain.user.model.LastName
import community.flock.workshop.domain.user.model.User

object UserConverter : Converter<UserValidationError, User, UserEntity> {
    override fun User.externalize() =
        UserEntity(
            email = "$email",
            firstName = "$firstName",
            lastName = "$lastName",
            birthDate = "$birthDate",
        )

    override fun UserEntity.internalize() =
        either {
            zipOrAccumulate(
                { Email(email).bind() },
                { FirstName(firstName).bind() },
                { LastName(lastName).bind() },
                { BirthDate(birthDate).bind() },
                ::User,
            )
        }
}
