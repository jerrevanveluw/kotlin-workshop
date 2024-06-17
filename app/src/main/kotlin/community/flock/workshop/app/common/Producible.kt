package community.flock.workshop.app.common

interface Producible<T : Any> {
    fun produce(): T
}
