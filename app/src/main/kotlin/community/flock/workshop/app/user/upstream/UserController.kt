package community.flock.workshop.app.user.upstream

import arrow.core.raise.context.bind
import community.flock.wirespec.generated.model.UserDto
import community.flock.workshop.app.common.handle
import community.flock.workshop.app.user.upstream.UserIdConsumer.validate
import community.flock.workshop.app.user.upstream.UserTransformer.validate
import community.flock.workshop.domain.user.HasUserService
import community.flock.workshop.domain.user.deleteUserByEmail
import community.flock.workshop.domain.user.getUserByEmail
import community.flock.workshop.domain.user.getUsers
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
        handle(UsersProducer) {
            context.userService
                .getUsers()
                .bind()
        }

    @GetMapping("/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun getUserById(
        @PathVariable("id") potentialId: String,
    ): UserDto =
        handle(UserTransformer) {
            val id = potentialId.validate().bind()
            context.userService
                .getUserByEmail(id)
                .bind()
        }

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun postUser(
        @RequestBody potentialUser: UserDto,
    ): UserDto =
        handle(UserTransformer) {
            val user = potentialUser.validate().bind()
            context.userService
                .saveUser(user)
                .bind()
        }

    @DeleteMapping("/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun deleteUserById(
        @PathVariable("id") potentialId: String,
    ): UserDto =
        handle(UserTransformer) {
            val id = potentialId.validate().bind()
            context.userService.deleteUserByEmail(id).bind()
        }
}
