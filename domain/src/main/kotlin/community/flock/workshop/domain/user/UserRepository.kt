package community.flock.workshop.domain.user

import arrow.core.Either
import community.flock.workshop.domain.error.Error
import community.flock.workshop.domain.user.model.Email
import community.flock.workshop.domain.user.model.User

interface UserRepository {
    suspend fun findAll(): Either<Error, List<User>>

    suspend fun findByEmail(email: Email): Either<Error, User>

    suspend fun save(user: User): Either<Error, User>

    suspend fun deleteByEmail(email: Email): Either<Error, User>
}

interface HasUserRepository {
    val userRepository: UserRepository
}
