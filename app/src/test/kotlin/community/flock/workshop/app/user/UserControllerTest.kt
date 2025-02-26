package community.flock.workshop.app.user

import com.fasterxml.jackson.databind.ObjectMapper
import community.flock.workshop.api.generated.DeleteUserByIdEndpoint
import community.flock.workshop.api.generated.GetUserByIdEndpoint
import community.flock.workshop.api.generated.GetUsersEndpoint
import community.flock.workshop.api.generated.PostUserEndpoint
import community.flock.workshop.app.environment.WithContainers
import community.flock.workshop.app.user.UserMother.USER_ID
import community.flock.workshop.app.user.UserMother.user
import community.flock.workshop.app.user.upstream.UserController
import io.kotest.common.runBlocking
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@SpringBootTest(webEnvironment = RANDOM_PORT)
class UserControllerTest : WithContainers() {
    @Autowired
    lateinit var userController: UserController

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Test
    fun testPostUser(): Unit =
        runBlocking {
            val request = PostUserEndpoint.Request(body = user)
            userController
                .postUser(request)
                .shouldBeTypeOf<PostUserEndpoint.Response200>()
                .body shouldBe user
        }

    @Test
    fun testGetUsers(): Unit =
        runBlocking {
            userController.postUser(PostUserEndpoint.Request(body = user))

            userController
                .getUsers(GetUsersEndpoint.Request)
                .shouldBeTypeOf<GetUsersEndpoint.Response200>()
                .body
                .first() shouldBe user
        }

    @Test
    fun testGetUserById(): Unit =
        runBlocking {
            userController.postUser(PostUserEndpoint.Request(body = user))

            val request = GetUserByIdEndpoint.Request(potentialUserId = USER_ID)
            userController
                .getUserById(request)
                .shouldBeTypeOf<GetUserByIdEndpoint.Response200>()
                .body shouldBe user
        }

    @Test
    fun testDeleteUserById(): Unit =
        runBlocking {
            userController.postUser(PostUserEndpoint.Request(body = user))

            val request = DeleteUserByIdEndpoint.Request(potentialUserId = USER_ID)
            userController
                .deleteUserById(request)
                .shouldBeTypeOf<DeleteUserByIdEndpoint.Response200>()
                .body shouldBe user
        }
}
