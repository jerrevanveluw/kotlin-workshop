package community.flock.workshop.user.upstream

import community.flock.workshop.user.UserContext
import community.flock.workshop.user.UserRepository
import community.flock.workshop.user.UserService.deleteUserById
import community.flock.workshop.user.UserService.getUserById
import community.flock.workshop.user.UserService.getUsers
import community.flock.workshop.user.UserService.saveUser
import community.flock.workshop.user.model.User
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UserController(userRepository: UserRepository) {
    private val context =
        object : UserContext {
            override val userRepository = userRepository
        }

    @GetMapping
    fun getUsers(): List<User> = context.getUsers().toList()

    @GetMapping("/{id}")
    fun getUserById(
        @PathVariable("id") id: String,
    ): User = context.getUserById(id)

    @PostMapping
    fun postUser(
        @RequestBody user: User,
    ): User = context.saveUser(user)

    @DeleteMapping("/{id}")
    fun deleteUserById(
        @PathVariable("id") id: String,
    ): User = context.deleteUserById(id)
}
