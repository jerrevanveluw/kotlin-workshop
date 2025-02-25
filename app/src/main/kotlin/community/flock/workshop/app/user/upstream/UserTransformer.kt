package community.flock.workshop.app.user.upstream

import community.flock.workshop.api.user.UserDto
import community.flock.workshop.app.common.Producer
import community.flock.workshop.app.common.Transformer
import community.flock.workshop.app.exception.BirthDateNotValid
import community.flock.workshop.app.exception.EmailNotValid
import community.flock.workshop.app.exception.FirstNameNotValid
import community.flock.workshop.app.exception.LastNameNotValid
import community.flock.workshop.app.user.upstream.UserTransformer.produce
import community.flock.workshop.domain.user.model.BirthDate
import community.flock.workshop.domain.user.model.Email
import community.flock.workshop.domain.user.model.FirstName
import community.flock.workshop.domain.user.model.LastName
import community.flock.workshop.domain.user.model.User

object UsersProducer : Producer<Iterable<User>, List<UserDto>> {
    override fun Iterable<User>.produce() = map { it.produce() }
}

object UserTransformer : Transformer<User, UserDto> {
    override fun User.produce() =
        UserDto(
            email = "$email",
            firstName = "$firstName",
            lastName = "$lastName",
            birthDate = "$birthDate",
        )

    override fun UserDto.consume() =
        run {
            val email = Email(email) ?: throw EmailNotValid()
            val firstName = FirstName(firstName) ?: throw FirstNameNotValid()
            val lastName = LastName(lastName) ?: throw LastNameNotValid()
            val birthDate = BirthDate(birthDate) ?: throw BirthDateNotValid()
            User(
                email = email,
                firstName = firstName,
                lastName = lastName,
                birthDate = birthDate,
            )
        }
}
