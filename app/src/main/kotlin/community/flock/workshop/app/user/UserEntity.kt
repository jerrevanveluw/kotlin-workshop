package community.flock.workshop.app.user

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "users")
data class UserEntity(
    @Id
    @Column(unique = true, nullable = false)
    val email: String,
    val firstName: String,
    val lastName: String,
    val birthDate: String,
)
