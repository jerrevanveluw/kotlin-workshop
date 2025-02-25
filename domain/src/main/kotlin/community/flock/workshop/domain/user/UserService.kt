package community.flock.workshop.domain.user

import community.flock.workshop.domain.user.model.Email
import community.flock.workshop.domain.user.model.User

interface UserContext : HasUserRepository

object UserService {
    suspend fun UserContext.getUsers() = userRepository.findAll()

    suspend fun UserContext.getUserByEmail(id: Email) = userRepository.findByEmail(id)

    suspend fun UserContext.saveUser(user: User) = userRepository.save(user)

    suspend fun UserContext.deleteUserByEmail(id: Email) = getUserByEmail(id).also { userRepository.deleteByEmail(id) }
}
