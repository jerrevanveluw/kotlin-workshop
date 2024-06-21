package community.flock.workshop.user.downstream

import arrow.core.Either
import arrow.core.raise.either
import community.flock.workshop.common.Externalizer
import community.flock.workshop.common.Internalizer
import community.flock.workshop.error.ValidationError
import community.flock.workshop.user.model.BirthDate
import community.flock.workshop.user.model.Email
import community.flock.workshop.user.model.FirstName
import community.flock.workshop.user.model.LastName
import community.flock.workshop.user.model.User

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
