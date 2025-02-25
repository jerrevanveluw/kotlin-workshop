package community.flock.workshop.app.user.downstream

import community.flock.workshop.app.user.downstream.UserConverter.externalize
import community.flock.workshop.app.user.downstream.UserConverter.internalize
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
    override fun findAll() = postgresRepository.findAll().map { it.internalize() }

    override fun findById(id: String) = postgresRepository.findById(id).getOrNull()?.internalize()

    override fun save(user: User) = postgresRepository.save(user.externalize()).internalize()

    override fun deleteById(id: String) = findById(id)?.also { postgresRepository.deleteById(id) }
}
