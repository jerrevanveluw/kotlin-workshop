package community.flock.workshop.app.user

import com.fasterxml.jackson.databind.ObjectMapper
import community.flock.workshop.api.generated.endpoint.DeleteUserById
import community.flock.workshop.api.generated.endpoint.GetUserById
import community.flock.workshop.api.generated.endpoint.GetUsers
import community.flock.workshop.api.generated.endpoint.PostUser
import community.flock.workshop.app.environment.WithContainers
import community.flock.workshop.app.user.UserMother.USER_ID
import community.flock.workshop.app.user.UserMother.user
import community.flock.workshop.app.user.upstream.UserController
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@SpringBootTest(webEnvironment = RANDOM_PORT)
class UserControllerTest : WithContainers {
    @Autowired
    lateinit var userController: UserController

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Test
    fun testPostUser(): Unit =
        runBlocking {
            val request = PostUser.Request(body = user)
            userController
                .postUser(request)
                .shouldBeTypeOf<PostUser.Response200>()
                .body shouldBe user
        }

    @Test
    fun testGetUsers(): Unit =
        runBlocking {
            userController.postUser(PostUser.Request(body = user))

            userController
                .getUsers(GetUsers.Request)
                .shouldBeTypeOf<GetUsers.Response200>()
                .body
                .first() shouldBe user
        }

    @Test
    fun testGetUserById(): Unit =
        runBlocking {
            userController.postUser(PostUser.Request(body = user))

            val request = GetUserById.Request(potentialUserId = USER_ID)
            userController
                .getUserById(request)
                .shouldBeTypeOf<GetUserById.Response200>()
                .body shouldBe user
        }

    @Test
    fun testDeleteUserById(): Unit =
        runBlocking {
            userController.postUser(PostUser.Request(body = user))

            val request = DeleteUserById.Request(potentialUserId = USER_ID)
            userController
                .deleteUserById(request)
                .shouldBeTypeOf<DeleteUserById.Response200>()
                .body shouldBe user
        }
}
