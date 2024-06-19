package community.flock.workshop.user

import community.flock.workshop.user.model.User

interface UserRepository {
    fun findAll(): List<User>

    fun findById(id: String): User?

    fun save(user: User): User

    fun deleteById(id: String): User?
}

interface HasUserRepository {
    val userRepository: UserRepository
}
