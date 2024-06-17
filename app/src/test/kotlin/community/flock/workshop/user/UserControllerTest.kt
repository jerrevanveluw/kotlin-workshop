package community.flock.workshop.user

import community.flock.workshop.environment.WithContainers
import community.flock.workshop.user.UserMother.USER_ID
import community.flock.workshop.user.UserMother.user
import io.kotest.common.runBlocking
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserControllerTest : WithContainers() {
    @Autowired
    lateinit var userController: UserController

    @Test
    fun testPostUser(): Unit =
        runBlocking {
            userController.postUser(user) shouldBe user.toDto()
        }

    @Test
    fun testGetUsers(): Unit =
        runBlocking {
            userController.postUser(user)
            userController.getUsers().shouldNotBeEmpty().first() shouldBe user.toDto()
        }

    @Test
    fun testGetUserById(): Unit =
        runBlocking {
            userController.postUser(user)
            userController.getUserById(USER_ID) shouldBe user.toDto()
        }

    @Test
    fun testDeleteUserById(): Unit =
        runBlocking {
            userController.postUser(user)
            userController.deleteUserById(USER_ID) shouldBe user.toDto()
        }
}
