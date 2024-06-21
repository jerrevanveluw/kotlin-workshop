package community.flock.workshop.user.upstream

import arrow.core.raise.either
import community.flock.workshop.common.handle
import community.flock.workshop.common.mapError
import community.flock.workshop.user.UserContext
import community.flock.workshop.user.UserRepository
import community.flock.workshop.user.UserService.deleteUserByEmail
import community.flock.workshop.user.UserService.getUserByEmail
import community.flock.workshop.user.UserService.getUsers
import community.flock.workshop.user.UserService.saveUser
import community.flock.workshop.user.model.Email
import community.flock.workshop.user.model.User
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UserController(
    userRepository: UserRepository,
) {
    private val context =
        object : UserContext {
            override val userRepository = userRepository
        }

    @GetMapping
    suspend fun getUsers(): List<User> =
        either {
            context
                .getUsers()
                .mapError()
                .bind()
                .toList()
        }.handle()

    @GetMapping("/{id}")
    suspend fun getUserById(
        @PathVariable("id") id: String,
    ): User =
        either {
            val email = Email(id).mapError().bind()
            context.getUserByEmail(email).mapError().bind()
        }.handle()

    @PostMapping
    suspend fun postUser(
        @RequestBody user: User,
    ): User =
        either {
            context.saveUser(user).mapError().bind()
        }.handle()

    @DeleteMapping("/{id}")
    suspend fun deleteUserById(
        @PathVariable("id") id: String,
    ): User =
        either {
            val email = Email(id).mapError().bind()
            context.deleteUserByEmail(email).mapError().bind()
        }.handle()
}
