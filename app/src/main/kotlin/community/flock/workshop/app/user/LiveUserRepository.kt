package community.flock.workshop.app.user

import community.flock.workshop.domain.user.UserRepository
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
    override fun findAll() = postgresRepository.findAll().map { it.toUser() }

    override fun findById(id: String) = postgresRepository.findById(id).getOrNull()?.toUser()

    override fun save(user: User) = postgresRepository.save(user.toUserEntity()).toUser()

    override fun deleteById(id: String) =
        findById(id)
            ?.also { postgresRepository.deleteById(id) }

    private fun UserEntity.toUser() =
        User(
            email = email,
            firstName = firstName,
            lastName = lastName,
            birthDate = birthDate,
        )

    private fun User.toUserEntity() =
        UserEntity(
            email = email,
            firstName = firstName,
            lastName = lastName,
            birthDate = birthDate,
        )
}
