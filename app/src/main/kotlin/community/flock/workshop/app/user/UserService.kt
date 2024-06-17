package community.flock.workshop.app.user

import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class UserService(
    private val userRepository: UserRepository,
) {
    fun getUsers(): List<User> = userRepository.findAll()

    fun getUserById(id: String) = userRepository.findById(id).getOrNull()

    fun saveUser(user: User) = userRepository.save(user)

    fun deleteUserById(id: String) =
        getUserById(id)
            ?.also { userRepository.deleteById(id) }
}
