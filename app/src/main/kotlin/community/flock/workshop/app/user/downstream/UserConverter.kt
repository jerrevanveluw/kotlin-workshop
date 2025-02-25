package community.flock.workshop.app.user.downstream

import community.flock.workshop.app.common.Converter
import community.flock.workshop.app.exception.BirthDateNotValid
import community.flock.workshop.app.exception.EmailNotValid
import community.flock.workshop.app.exception.FirstNameNotValid
import community.flock.workshop.app.exception.LastNameNotValid
import community.flock.workshop.domain.user.model.BirthDate
import community.flock.workshop.domain.user.model.Email
import community.flock.workshop.domain.user.model.FirstName
import community.flock.workshop.domain.user.model.LastName
import community.flock.workshop.domain.user.model.User

object UserConverter : Converter<User, UserEntity> {
    override fun User.externalize() =
        UserEntity(
            email = "$email",
            firstName = "$firstName",
            lastName = "$lastName",
            birthDate = "$birthDate",
        )

    override fun UserEntity.internalize() =
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
