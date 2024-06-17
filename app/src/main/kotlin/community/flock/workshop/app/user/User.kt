package community.flock.workshop.app.user

import community.flock.workshop.api.user.UserDto
import community.flock.workshop.app.common.Producible
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "users")
data class User(
    @Id
    @Column(unique = true, nullable = false)
    val email: String,
    val firstName: String,
    val lastName: String,
    val birthDate: String,
) : Producible<UserDto> {
    override fun produce() =
        UserDto(
            email = email,
            firstName = firstName,
            lastName = lastName,
            birthDate = birthDate,
        )
}
