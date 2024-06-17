package community.flock.workshop.user

import community.flock.workshop.common.Producible
import community.flock.workshop.exception.UserNotFoundException
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
    @GetMapping
    fun getUsers(): List<UserDto> =
        userService
            .getUsers()
            .map { it.toDto() }

    @GetMapping("/{id}")
    fun getUserById(
        @PathVariable("id") id: String,
    ): UserDto =
        handleNullable {
            userService.getUserById(id)
        }

    @PostMapping
    fun postUser(
        @RequestBody user: User,
    ): UserDto =
        userService
            .saveUser(user)
            .toDto()

    @DeleteMapping("/{id}")
    fun deleteUserById(
        @PathVariable("id") id: String,
    ): UserDto =
        handleNullable {
            userService.deleteUserById(id)
        }

    private fun <T : Any> handleNullable(block: () -> Producible<T>?) = block()?.toDto() ?: throw UserNotFoundException()
}
