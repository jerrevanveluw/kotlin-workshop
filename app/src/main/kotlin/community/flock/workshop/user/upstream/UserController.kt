package community.flock.workshop.user.upstream

import community.flock.workshop.common.Producer
import community.flock.workshop.exception.UserNotFoundException
import community.flock.workshop.user.UserContext
import community.flock.workshop.user.UserDto
import community.flock.workshop.user.UserRepository
import community.flock.workshop.user.UserService.deleteUserById
import community.flock.workshop.user.UserService.getUserById
import community.flock.workshop.user.UserService.getUsers
import community.flock.workshop.user.UserService.saveUser
import community.flock.workshop.user.model.User
import community.flock.workshop.user.upstream.UserProducer.produce
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
    private val liveUserRepository: UserRepository,
) {
    private val context =
        object : UserContext {
            override val userRepository = liveUserRepository
        }

    @GetMapping
    fun getUsers(): List<UserDto> =
        context
            .getUsers()
            .map { it.produce() }

    @GetMapping("/{id}")
    fun getUserById(
        @PathVariable("id") id: String,
    ): UserDto =
        handleNullable(UserProducer) {
            context.getUserById(id)
        }

    @PostMapping
    fun postUser(
        @RequestBody user: User,
    ): UserDto =
        context
            .saveUser(user)
            .produce()

    @DeleteMapping("/{id}")
    fun deleteUserById(
        @PathVariable("id") id: String,
    ): UserDto =
        handleNullable(UserProducer) {
            context.deleteUserById(id)
        }

    private fun <T : Any, R : Any> handleNullable(
        producer: Producer<T, R>,
        block: () -> T?,
    ) = with(producer) {
        block()?.produce() ?: throw UserNotFoundException()
    }
}
