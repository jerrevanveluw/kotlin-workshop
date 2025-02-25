package community.flock.workshop.app.user.upstream

import arrow.core.Either
import arrow.core.raise.either
import community.flock.workshop.api.user.UserDto
import community.flock.workshop.app.common.Consumer
import community.flock.workshop.app.common.Producer
import community.flock.workshop.domain.error.ValidationError
import community.flock.workshop.domain.user.model.BirthDate
import community.flock.workshop.domain.user.model.Email
import community.flock.workshop.domain.user.model.FirstName
import community.flock.workshop.domain.user.model.LastName
import community.flock.workshop.domain.user.model.User

object UserProducer : Producer<User, UserDto> {
    override fun User.produce() =
        UserDto(
            email = "$email",
            firstName = "$firstName",
            lastName = "$lastName",
            birthDate = "$birthDate",
        )
}

object UserConsumer : Consumer<UserDto, Either<ValidationError, User>> {
    override fun UserDto.consume() =
        either {
            User(
                email = Email(email).bind(),
                firstName = FirstName(firstName).bind(),
                lastName = LastName(lastName).bind(),
                birthDate = BirthDate(birthDate).bind(),
            )
        }
}
