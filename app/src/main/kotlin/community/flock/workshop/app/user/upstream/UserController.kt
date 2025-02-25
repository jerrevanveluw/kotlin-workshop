package community.flock.workshop.app.user.upstream

import arrow.core.raise.either
import community.flock.workshop.api.user.UserDto
import community.flock.workshop.app.common.Producer
import community.flock.workshop.app.common.handle
import community.flock.workshop.app.common.mapError
import community.flock.workshop.app.user.upstream.UserConsumer.consume
import community.flock.workshop.app.user.upstream.UserProducer.produce
import community.flock.workshop.domain.user.UserContext
import community.flock.workshop.domain.user.UserRepository
import community.flock.workshop.domain.user.UserService.deleteUserByEmail
import community.flock.workshop.domain.user.UserService.getUserByEmail
import community.flock.workshop.domain.user.UserService.getUsers
import community.flock.workshop.domain.user.UserService.saveUser
import community.flock.workshop.domain.user.model.Email
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
    userRepository: UserRepository,
) {
    private val context =
        object : UserContext {
            override val userRepository = userRepository
        }

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun getUsers(): List<UserDto> =
        either {
            context
                .getUsers()
                .mapError()
                .bind()
                .toList()
        }.handle().map { it.produce() }

    @GetMapping("/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun getUserById(
        @PathVariable("id") id: String,
    ): UserDto =
        either {
            val email = Email(id).mapError().bind()
            context.getUserByEmail(email).mapError().bind()
        }.handle().produce()

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun postUser(
        @RequestBody potentialUser: UserDto,
    ): UserDto =
        either {
            val user = potentialUser.consume().mapError().bind()
            context.saveUser(user).mapError().bind()
        }.handle().produce()

    @DeleteMapping("/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun deleteUserById(
        @PathVariable("id") id: String,
    ): UserDto =
        either {
            val email = Email(id).mapError().bind()
            context.deleteUserByEmail(email).mapError().bind()
        }.handle().produce()

    private fun <T : Any, R : Any> handle(
        producer: Producer<T, R>,
        block: () -> T,
    ): R =
        with(producer) {
            block().produce()
        }
}
