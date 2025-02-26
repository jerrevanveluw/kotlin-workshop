package community.flock.workshop.app.user.upstream

import arrow.core.EitherNel
import arrow.core.raise.either
import arrow.core.raise.zipOrAccumulate
import arrow.core.toEitherNel
import community.flock.workshop.api.generated.model.UserDto
import community.flock.workshop.app.common.Producer
import community.flock.workshop.app.common.Transformer
import community.flock.workshop.app.common.Validator
import community.flock.workshop.app.user.upstream.UserIdConsumer.consume
import community.flock.workshop.app.user.upstream.UserTransformer.produce
import community.flock.workshop.domain.error.EmailValidationError
import community.flock.workshop.domain.error.UserValidationError
import community.flock.workshop.domain.user.model.BirthDate
import community.flock.workshop.domain.user.model.Email
import community.flock.workshop.domain.user.model.FirstName
import community.flock.workshop.domain.user.model.LastName
import community.flock.workshop.domain.user.model.User

object UsersProducer : Producer<Iterable<User>, List<UserDto>> {
    override fun Iterable<User>.produce(): List<UserDto> = map { it.produce() }
}

object UserTransformer : Transformer<UserValidationError, User, UserDto> {
    override fun User.produce(): UserDto =
        UserDto(
            email = "$email",
            firstName = "$firstName",
            lastName = "$lastName",
            birthDate = "$birthDate",
        )

    override fun UserDto.consume(): EitherNel<UserValidationError, User> =
        either {
            zipOrAccumulate(
                { email.consume().bindNel() },
                { FirstName(firstName).bind() },
                { LastName(lastName).bind() },
                { BirthDate(birthDate).bind() },
                ::User,
            )
        }
}

object UserIdConsumer : Validator<EmailValidationError, String, Email> {
    override fun String.consume() = Email(this).toEitherNel()
}
