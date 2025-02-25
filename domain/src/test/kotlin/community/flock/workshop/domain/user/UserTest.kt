package community.flock.workshop.domain.user

import community.flock.workshop.domain.common.component1
import community.flock.workshop.domain.error.BirthDayError
import community.flock.workshop.domain.error.EmailValidationError
import community.flock.workshop.domain.error.FirstNameError
import community.flock.workshop.domain.error.LastNameError
import community.flock.workshop.domain.user.model.BirthDate
import community.flock.workshop.domain.user.model.Email
import community.flock.workshop.domain.user.model.FirstName
import community.flock.workshop.domain.user.model.LastName
import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class UserTest {
    @Test
    fun testEmail() {
        val (email) = Email("john.doe@example.com").shouldBeRight()
        email shouldBe "john.doe@example.com"

        Email("email") shouldBeLeft EmailValidationError.Invalid
    }

    @Test
    fun testFirstName() {
        val (firstName) = FirstName("FirstName").shouldBeRight()
        firstName shouldBe "FirstName"

        FirstName("") shouldBeLeft FirstNameError.Empty
    }

    @Test
    fun testLastName() {
        val (lastName) = LastName("LastName").shouldBeRight()
        lastName shouldBe "LastName"

        LastName("") shouldBeLeft LastNameError.Empty
    }

    @Test
    fun testBirthDate() {
        val (birthDate) = BirthDate("2000-01-01").shouldBeRight()
        "$birthDate" shouldBe "2000-01-01"

        BirthDate("birthDate") shouldBeLeft BirthDayError.Invalid
    }
}
