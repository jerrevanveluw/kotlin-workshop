package community.flock.workshop.domain.user

import community.flock.workshop.domain.common.component1
import community.flock.workshop.domain.user.model.BirthDate
import community.flock.workshop.domain.user.model.Email
import community.flock.workshop.domain.user.model.FirstName
import community.flock.workshop.domain.user.model.LastName
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class UserTest {
    @Test
    fun testEmail() {
        val (email) = Email("john.doe@example.com")!!
        email shouldBe "john.doe@example.com"

        Email("email").shouldBeNull()
    }

    @Test
    fun testFirstName() {
        val (firstName) = FirstName("firstName")!!
        firstName shouldBe "firstName"

        FirstName("").shouldBeNull()
    }

    @Test
    fun testLastName() {
        val (lastName) = LastName("lastName")!!
        lastName shouldBe "lastName"

        LastName("").shouldBeNull()
    }

    @Test
    fun testBirthDate() {
        val birthDate = BirthDate("2000-01-01")!!.toString()
        birthDate shouldBe "2000-01-01"

        BirthDate("birthDate").shouldBeNull()
    }
}
