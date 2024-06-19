package community.flock.workshop.common

interface Producer<T : Any, R : Any> {
    fun T.toDto(): R
}
