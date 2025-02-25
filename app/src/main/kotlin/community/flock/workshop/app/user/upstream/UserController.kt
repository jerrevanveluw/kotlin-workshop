package community.flock.workshop.app.user.upstream

import community.flock.workshop.api.user.UserDto
import community.flock.workshop.app.common.Producer
import community.flock.workshop.app.exception.UserNotFoundException
import community.flock.workshop.app.user.upstream.UserTransformer.consume
import community.flock.workshop.domain.error.UserNotFound
import community.flock.workshop.domain.user.UserContext
import community.flock.workshop.domain.user.UserRepository
import community.flock.workshop.domain.user.UserService.deleteUserById
import community.flock.workshop.domain.user.UserService.getUserById
import community.flock.workshop.domain.user.UserService.getUsers
import community.flock.workshop.domain.user.UserService.saveUser
import org.springframework.http.MediaType
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

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getUsers(): List<UserDto> =
        handle(UsersProducer) {
            context.getUsers()
        }

    @GetMapping("/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getUserById(
        @PathVariable("id") id: String,
    ): UserDto =
        handle(UserTransformer) {
            context.getUserById(id)
        }

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun postUser(
        @RequestBody potentialUser: UserDto,
    ): UserDto =
        handle(UserTransformer) {
            val user = potentialUser.consume()
            context.saveUser(user)
        }

    @DeleteMapping("/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun deleteUserById(
        @PathVariable("id") id: String,
    ): UserDto =
        handle(UserTransformer) {
            context.deleteUserById(id)
        }

    private fun <T : Any, R : Any> handle(
        producer: Producer<T, R>,
        block: () -> T,
    ): R =
        with(producer) {
            try {
                block()
            } catch (e: UserNotFound) {
                throw UserNotFoundException()
            }.produce()
        }
}
