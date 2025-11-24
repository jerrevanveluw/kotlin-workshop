package community.flock.workshop.app.user

import community.flock.workshop.api.user.UserDto
import community.flock.workshop.app.exception.UserNotFoundException
import community.flock.workshop.domain.error.UserNotFound
import community.flock.workshop.domain.user.HasUserService
import community.flock.workshop.domain.user.deleteUserById
import community.flock.workshop.domain.user.getUserById
import community.flock.workshop.domain.user.getUsers
import community.flock.workshop.domain.user.model.User
import community.flock.workshop.domain.user.saveUser
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
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
    @GetMapping(produces = [APPLICATION_JSON_VALUE])
    fun getUsers(): List<UserDto> =
        context
            .userService
            .getUsers()
            .map { it.produce() }

    @GetMapping("/{id}", produces = [APPLICATION_JSON_VALUE])
    fun getUserById(
        @PathVariable id: String,
    ): UserDto =
        handleThrowable {
            context.userService.getUserById(id)
        }

    @PostMapping(consumes = [APPLICATION_JSON_VALUE], produces = [APPLICATION_JSON_VALUE])
    fun postUser(
        @RequestBody user: UserDto,
    ): UserDto =
        user
            .consume()
            .let { context.userService.saveUser(it) }
            .produce()

    @DeleteMapping("/{id}", produces = [APPLICATION_JSON_VALUE])
    fun deleteUserById(
        @PathVariable id: String,
    ): UserDto =
        handleThrowable {
            context.userService.deleteUserById(id)
        }

    private fun handleThrowable(block: () -> User) =
        try {
            block()
        } catch (_: UserNotFound) {
            throw UserNotFoundException()
        }.produce()
}

private fun UserDto.consume() =
    User(
        email = email,
        firstName = firstName,
        lastName = lastName,
        birthDate = birthDate,
    )

private fun User.produce() =
    UserDto(
        email = email,
        firstName = firstName,
        lastName = lastName,
        birthDate = birthDate,
    )
