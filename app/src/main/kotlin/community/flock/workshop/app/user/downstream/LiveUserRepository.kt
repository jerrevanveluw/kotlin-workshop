package community.flock.workshop.app.user.downstream

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensureNotNull
import community.flock.workshop.app.user.downstream.UserExternalizer.externalize
import community.flock.workshop.app.user.downstream.UserInternalizer.internalize
import community.flock.workshop.domain.error.Error
import community.flock.workshop.domain.error.UserNotFound
import community.flock.workshop.domain.user.UserRepository
import community.flock.workshop.domain.user.model.Email
import community.flock.workshop.domain.user.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import kotlin.jvm.optionals.getOrNull

@Repository
interface PostgresRepository : JpaRepository<UserEntity, String>

@Repository
class LiveUserRepository(
    private val postgresRepository: PostgresRepository,
) : UserRepository {
    override suspend fun findAll() =
        either {
            postgresRepository
                .findAll()
                .map { it.internalize().bind() }
        }

    override suspend fun findByEmail(email: Email): Either<Error, User> =
        either {
            val maybeUser = postgresRepository.findById(email.value).getOrNull()
            ensureNotNull(maybeUser) { UserNotFound(email) }
            maybeUser.internalize().bind()
        }

    override suspend fun save(user: User): Either<Error, User> =
        either {
            postgresRepository
                .save(user.externalize())
                .internalize()
                .bind()
        }

    override suspend fun deleteByEmail(email: Email): Either<Error, User> =
        either {
            findByEmail(email)
                .bind()
                .also { postgresRepository.deleteById(email.value) }
        }
}
