package community.flock.workshop.app.user

import community.flock.workshop.api.user.UserDto
import community.flock.workshop.app.exception.UserNotFoundException
import community.flock.workshop.domain.error.UserNotFound
import community.flock.workshop.domain.user.UserContext
import community.flock.workshop.domain.user.UserRepository
import community.flock.workshop.domain.user.UserService.deleteUserById
import community.flock.workshop.domain.user.UserService.getUserById
import community.flock.workshop.domain.user.UserService.getUsers
import community.flock.workshop.domain.user.UserService.saveUser
import community.flock.workshop.domain.user.model.User
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
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

    @GetMapping(produces = [APPLICATION_JSON_VALUE])
    fun getUsers(): List<UserDto> =
        context
            .getUsers()
            .map { it.produce() }

    @GetMapping("/{id}", produces = [APPLICATION_JSON_VALUE])
    fun getUserById(
        @PathVariable("id") id: String,
    ): UserDto =
        handleThrowable {
            context.getUserById(id)
        }

    @PostMapping(consumes = [APPLICATION_JSON_VALUE], produces = [APPLICATION_JSON_VALUE])
    fun postUser(
        @RequestBody user: UserDto,
    ): UserDto =
        user
            .consume()
            .let { context.saveUser(it) }
            .produce()

    @DeleteMapping("/{id}", produces = [APPLICATION_JSON_VALUE])
    fun deleteUserById(
        @PathVariable("id") id: String,
    ): UserDto =
        handleThrowable {
            context.deleteUserById(id)
        }

    private fun handleThrowable(block: () -> User) =
        try {
            block()
        } catch (e: UserNotFound) {
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
