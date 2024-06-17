package community.flock.workshop.user

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
    fun getUsers(): List<User> = userService.getUsers().toList()

    @GetMapping("/{id}")
    fun getUserById(
        @PathVariable("id") id: String,
    ): User = userService.getUserById(id) ?: throw UserNotFoundException()

    @PostMapping
    fun postUser(
        @RequestBody user: User,
    ): User = userService.saveUser(user)

    @DeleteMapping("/{id}")
    fun deleteUserById(
        @PathVariable("id") id: String,
    ): User = userService.deleteUserById(id) ?: throw UserNotFoundException()
}
