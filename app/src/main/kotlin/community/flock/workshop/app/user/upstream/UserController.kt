package community.flock.workshop.app.user.upstream

import arrow.core.raise.either
import community.flock.workshop.api.user.UserDto
import community.flock.workshop.app.common.handle
import community.flock.workshop.app.common.mapError
import community.flock.workshop.app.user.upstream.UserConsumer.consume
import community.flock.workshop.app.user.upstream.UserProducer.produce
import community.flock.workshop.domain.user.HasUserService
import community.flock.workshop.domain.user.deleteUserByEmail
import community.flock.workshop.domain.user.getUserByEmail
import community.flock.workshop.domain.user.getUsers
import community.flock.workshop.domain.user.model.Email
import community.flock.workshop.domain.user.saveUser
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

interface UserControllerDI : HasUserService

@RestController
@RequestMapping("/api/users")
class UserController(
    private val context: UserControllerDI,
) {
    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun getUsers(): List<UserDto> =
        either {
            context
                .userService
                .getUsers()
                .mapError()
                .bind()
                .toList()
        }.handle().map { it.produce() }

    @GetMapping("/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun getUserById(
        @PathVariable id: String,
    ): UserDto =
        either {
            val email = Email(id).mapError().bind()
            context.userService
                .getUserByEmail(email)
                .mapError()
                .bind()
        }.handle().produce()

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun postUser(
        @RequestBody potentialUser: UserDto,
    ): UserDto =
        either {
            val user = potentialUser.consume().mapError().bind()
            context.userService
                .saveUser(user)
                .mapError()
                .bind()
        }.handle().produce()

    @DeleteMapping("/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun deleteUserById(
        @PathVariable id: String,
    ): UserDto =
        either {
            val email = Email(id).mapError().bind()
            context.userService
                .deleteUserByEmail(email)
                .mapError()
                .bind()
        }.handle().produce()
}
