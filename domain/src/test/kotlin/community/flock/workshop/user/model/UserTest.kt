package community.flock.workshop.user.model

import community.flock.workshop.common.component1
import community.flock.workshop.error.BirthDayError
import community.flock.workshop.error.EmailValidationError
import community.flock.workshop.error.FirstNameError
import community.flock.workshop.error.LastNameError
import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class UserTest {
    @Test
    fun testEmail() {
        val (email) = Email("john.doe@example.com").getOrNull()!!
        email shouldBe "john.doe@example.com"

        Email("email").shouldBeLeft(EmailValidationError.Invalid)
    }

    @Test
    fun testFirstName() {
        val (firstName) = FirstName("firstName").getOrNull()!!
        firstName shouldBe "firstName"

        FirstName("").shouldBeLeft(FirstNameError.Empty)
    }

    @Test
    fun testLastName() {
        val (lastName) = LastName("lastName").getOrNull()!!
        lastName shouldBe "lastName"

        LastName("").shouldBeLeft(LastNameError.Empty)
    }

    @Test
    fun testBirthDate() {
        val birthDate = BirthDate("2000-01-01").getOrNull()!!.toString()
        birthDate shouldBe "2000-01-01"

        BirthDate("birthDate").shouldBeLeft(BirthDayError.Invalid)
    }
}
