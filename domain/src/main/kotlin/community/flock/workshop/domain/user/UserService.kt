package community.flock.workshop.domain.user

import community.flock.workshop.domain.error.UserNotFound
import community.flock.workshop.domain.user.model.User

interface UserService : HasUserRepository

fun UserService.getUsers(): List<User> = userRepository.findAll()

fun UserService.getUserById(id: String) = userRepository.findById(id) ?: throw UserNotFound(id)

fun UserService.saveUser(user: User) = userRepository.save(user)

fun UserService.deleteUserById(id: String) = getUserById(id).also { userRepository.deleteById(id) }

interface HasUserService {
    val userService: UserService
}
