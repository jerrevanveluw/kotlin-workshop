package community.flock.workshop.common

interface Producible<T : Any> {
    fun toDto(): T
}
