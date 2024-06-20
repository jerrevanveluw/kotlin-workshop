package community.flock.workshop.user.downstream

import community.flock.workshop.common.Converter
import community.flock.workshop.exception.BirthDateNotValid
import community.flock.workshop.exception.EmailNotValid
import community.flock.workshop.exception.FirstNameNotValid
import community.flock.workshop.exception.LastNameNotValid
import community.flock.workshop.user.model.BirthDate
import community.flock.workshop.user.model.Email
import community.flock.workshop.user.model.FirstName
import community.flock.workshop.user.model.LastName
import community.flock.workshop.user.model.User

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
