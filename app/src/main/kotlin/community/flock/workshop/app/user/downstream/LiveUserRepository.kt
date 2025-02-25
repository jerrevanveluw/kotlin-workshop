package community.flock.workshop.app.user.downstream

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensureNotNull
import community.flock.workshop.app.user.downstream.UserConverter.externalize
import community.flock.workshop.app.user.downstream.UserConverter.verify
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
    override suspend fun findAll(): Either<Error, List<User>> =
        either {
            postgresRepository
                .findAll()
                .map { it.verify().bind() }
        }

    override suspend fun findByEmail(email: Email): Either<Error, User> =
        either {
            email.value
                .let(postgresRepository::findById)
                .getOrNull()
                .let { ensureNotNull(it) { UserNotFound(email) } }
                .verify()
                .bind()
        }

    override suspend fun save(user: User): Either<Error, User> =
        either {
            user
                .externalize()
                .let(postgresRepository::save)
                .verify()
                .bind()
        }

    override suspend fun deleteByEmail(email: Email): Either<Error, User> =
        findByEmail(email)
            .onRight { postgresRepository.deleteById(it.email.value) }
}
