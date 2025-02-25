package community.flock.workshop.domain.user

import community.flock.workshop.domain.error.UserNotFound
import community.flock.workshop.domain.user.model.User

interface UserContext : HasUserRepository

object UserService {
    fun UserContext.getUsers(): List<User> = userRepository.findAll()

    fun UserContext.getUserById(id: String) = userRepository.findById(id) ?: throw UserNotFound(id)

    fun UserContext.saveUser(user: User) = userRepository.save(user)

    fun UserContext.deleteUserById(id: String) = getUserById(id).also { userRepository.deleteById(id) }
}
