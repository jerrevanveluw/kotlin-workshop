package community.flock.workshop.user

import arrow.core.Either
import community.flock.workshop.error.Error
import community.flock.workshop.user.model.Email
import community.flock.workshop.user.model.User

interface UserRepository {
    suspend fun findAll(): Either<Error, List<User>>

    suspend fun findByEmail(email: Email): Either<Error, User>

    suspend fun save(user: User): Either<Error, User>

    suspend fun deleteByEmail(email: Email): Either<Error, User>
}

interface HasUserRepository {
    val userRepository: UserRepository
}
