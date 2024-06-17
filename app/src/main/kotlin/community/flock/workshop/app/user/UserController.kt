package community.flock.workshop.app.user

import community.flock.workshop.api.user.UserDto
import community.flock.workshop.app.common.Producible
import community.flock.workshop.app.exception.UserNotFoundException
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
    private val userService: UserService,
) {
    @GetMapping(produces = [APPLICATION_JSON_VALUE])
    fun getUsers(): List<UserDto> =
        userService
            .getUsers()
            .map { it.produce() }

    @GetMapping("/{id}", produces = [APPLICATION_JSON_VALUE])
    fun getUserById(
        @PathVariable("id") id: String,
    ): UserDto =
        handleNullable {
            userService.getUserById(id)
        }

    @PostMapping(consumes = [APPLICATION_JSON_VALUE], produces = [APPLICATION_JSON_VALUE])
    fun postUser(
        @RequestBody user: UserDto,
    ): UserDto =
        user
            .consume()
            .let(userService::saveUser)
            .produce()

    @DeleteMapping("/{id}", produces = [APPLICATION_JSON_VALUE])
    fun deleteUserById(
        @PathVariable("id") id: String,
    ): UserDto =
        handleNullable {
            userService.deleteUserById(id)
        }

    private fun <T : Any> handleNullable(block: () -> Producible<T>?) = block()?.produce() ?: throw UserNotFoundException()
}

private fun UserDto.consume() =
    User(
        email = email,
        firstName = firstName,
        lastName = lastName,
        birthDate = birthDate,
    )
