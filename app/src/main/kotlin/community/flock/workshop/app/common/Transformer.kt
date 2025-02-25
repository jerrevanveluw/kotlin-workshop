package community.flock.workshop.app.common

import arrow.core.EitherNel
import community.flock.workshop.domain.error.ValidationError
import community.flock.workshop.domain.error.ValidationErrors

/**
 * Placeholder interface to define upstream transformers.
 * (i.e. used by resource or controller implementations)
 */
interface Transformer<ERROR : ValidationError, DOMAIN : Any, EXTERNAL : Any> :
    Producer<DOMAIN, EXTERNAL>,
    Validator<ERROR, EXTERNAL, DOMAIN>

interface Producer<DOMAIN : Any, EXTERNAL : Any> {
    fun DOMAIN.produce(): EXTERNAL
}

interface Consumer<EXTERNAL : Any, DOMAIN : Any> {
    fun EXTERNAL.consume(): DOMAIN
}

interface Validator<ERROR : ValidationError, EXTERNAL : Any, DOMAIN : Any> : Consumer<EXTERNAL, EitherNel<ERROR, DOMAIN>> {
    fun EXTERNAL.validate() = consume().mapLeft(::ValidationErrors)
}
