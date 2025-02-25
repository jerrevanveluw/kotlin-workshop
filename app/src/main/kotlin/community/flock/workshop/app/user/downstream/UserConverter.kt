package community.flock.workshop.app.user.downstream

import arrow.core.Either
import arrow.core.raise.either
import community.flock.workshop.app.common.Externalizer
import community.flock.workshop.app.common.Internalizer
import community.flock.workshop.domain.error.ValidationError
import community.flock.workshop.domain.user.model.BirthDate
import community.flock.workshop.domain.user.model.Email
import community.flock.workshop.domain.user.model.FirstName
import community.flock.workshop.domain.user.model.LastName
import community.flock.workshop.domain.user.model.User

object UserExternalizer : Externalizer<User, UserEntity> {
    override fun User.externalize() =
        UserEntity(
            email = "$email",
            firstName = "$firstName",
            lastName = "$lastName",
            birthDate = "$birthDate",
        )
}

object UserInternalizer : Internalizer<UserEntity, Either<ValidationError, User>> {
    override fun UserEntity.internalize() =
        either {
            User(
                email = Email(email).bind(),
                firstName = FirstName(firstName).bind(),
                lastName = LastName(lastName).bind(),
                birthDate = BirthDate(birthDate).bind(),
            )
        }
}
