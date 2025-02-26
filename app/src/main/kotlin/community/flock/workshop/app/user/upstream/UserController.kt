package community.flock.workshop.app.user.upstream

import community.flock.wirespec.generated.UserDto
import community.flock.workshop.app.common.handle
import community.flock.workshop.app.user.upstream.UserIdConsumer.validate
import community.flock.workshop.app.user.upstream.UserTransformer.validate
import community.flock.workshop.domain.user.UserContext
import community.flock.workshop.domain.user.UserRepository
import community.flock.workshop.domain.user.UserService.deleteUserByEmail
import community.flock.workshop.domain.user.UserService.getUserByEmail
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
    userRepository: UserRepository,
) {
    private val context =
        object : UserContext {
            override val userRepository = userRepository
        }

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun getUsers(): List<UserDto> =
        handle(UsersProducer) {
            context.getUsers().bind()
        }

    @GetMapping("/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun getUserById(
        @PathVariable("id") potentialId: String,
    ): UserDto =
        handle(UserTransformer) {
            val id = potentialId.validate().bind()
            context.getUserByEmail(id).bind()
        }

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun postUser(
        @RequestBody potentialUser: UserDto,
    ): UserDto =
        handle(UserTransformer) {
            val user = potentialUser.validate().bind()
            context.saveUser(user).bind()
        }

    @DeleteMapping("/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun deleteUserById(
        @PathVariable("id") potentialId: String,
    ): UserDto =
        handle(UserTransformer) {
            val id = potentialId.validate().bind()
            context.deleteUserByEmail(id).bind()
        }
}
