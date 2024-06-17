package community.flock.workshop.app.user

import community.flock.workshop.app.environment.WithContainers
import community.flock.workshop.app.user.UserMother.USER_ID
import community.flock.workshop.app.user.UserMother.user
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@SpringBootTest(webEnvironment = RANDOM_PORT)
class UserControllerTest : WithContainers {
    @Autowired
    lateinit var userController: UserController

    @Test
    fun testPostUser(): Unit =
        runBlocking {
            userController.postUser(user) shouldBe user
        }

    @Test
    fun testGetUsers(): Unit =
        runBlocking {
            userController.postUser(user)
            userController.getUsers().shouldNotBeEmpty().first() shouldBe user
        }

    @Test
    fun testGetUserById(): Unit =
        runBlocking {
            userController.postUser(user)
            userController.getUserById(USER_ID) shouldBe user
        }

    @Test
    fun testDeleteUserById(): Unit =
        runBlocking {
            userController.postUser(user)
            userController.deleteUserById(USER_ID) shouldBe user
        }
}
