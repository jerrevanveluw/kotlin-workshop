package community.flock.workshop.domain.user

import community.flock.workshop.domain.user.model.User

interface UserRepository {
    fun findAll(): List<User>

    fun findById(id: String): User?

    fun save(user: User): User

    fun deleteById(id: String): User?
}

interface HasUserRepository {
    val userRepository: UserRepository
}
