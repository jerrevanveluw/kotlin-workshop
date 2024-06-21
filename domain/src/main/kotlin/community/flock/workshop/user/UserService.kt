package community.flock.workshop.user

import community.flock.workshop.user.model.Email
import community.flock.workshop.user.model.User

interface UserContext : HasUserRepository

object UserService {
    suspend fun UserContext.getUsers() = userRepository.findAll()

    suspend fun UserContext.getUserByEmail(id: Email) = userRepository.findByEmail(id)

    suspend fun UserContext.saveUser(user: User) = userRepository.save(user)

    suspend fun UserContext.deleteUserByEmail(id: Email) = getUserByEmail(id).also { userRepository.deleteByEmail(id) }
}
