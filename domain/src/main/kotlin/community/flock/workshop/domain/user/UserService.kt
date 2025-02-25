package community.flock.workshop.domain.user

import community.flock.workshop.domain.user.model.Email
import community.flock.workshop.domain.user.model.User

interface UserService : HasUserRepository

suspend fun UserService.getUsers() = userRepository.findAll()

suspend fun UserService.getUserByEmail(id: Email) = userRepository.findByEmail(id)

suspend fun UserService.saveUser(user: User) = userRepository.save(user)

suspend fun UserService.deleteUserByEmail(id: Email) = getUserByEmail(id).also { userRepository.deleteByEmail(id) }

interface HasUserService {
    val userService: UserService
}
