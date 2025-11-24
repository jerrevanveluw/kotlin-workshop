package community.flock.workshop.domain.error

sealed interface Error {
    val message: String
}

sealed class DomainError(
    override val message: String,
) : RuntimeException(message),
    Error
