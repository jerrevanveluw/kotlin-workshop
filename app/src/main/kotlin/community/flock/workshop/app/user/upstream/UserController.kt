package community.flock.workshop.app.user.upstream

import community.flock.workshop.api.user.UserDto
import community.flock.workshop.app.common.Producer
import community.flock.workshop.app.user.upstream.UserTransformer.consume
import community.flock.workshop.domain.user.HasUserService
import community.flock.workshop.domain.user.deleteUserById
import community.flock.workshop.domain.user.getUserById
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
    fun getUsers(): List<UserDto> =
        handle(UsersProducer) {
            context
                .userService
                .getUsers()
        }

    @GetMapping("/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getUserById(
        @PathVariable id: String,
    ): UserDto =
        handle(UserTransformer) {
            context.userService.getUserById(id)
        }

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun postUser(
        @RequestBody potentialUser: UserDto,
    ): UserDto =
        handle(UserTransformer) {
            val user = potentialUser.consume()
            context.userService.saveUser(user)
        }

    @DeleteMapping("/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun deleteUserById(
        @PathVariable id: String,
    ): UserDto =
        handle(UserTransformer) {
            context.userService.deleteUserById(id)
        }

    private fun <T : Any, R : Any> handle(
        producer: Producer<T, R>,
        block: () -> T,
    ): R =
        with(producer) {
            block().produce()
        }
}
