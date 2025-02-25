package community.flock.workshop.app.common

import arrow.core.EitherNel
import community.flock.workshop.domain.error.ValidationError
import community.flock.workshop.domain.error.ValidationErrors

/**
 * Placeholder interface to define downstream converters.
 * (i.e. used by adapter or repository implementations)
 */
interface Converter<ERROR : ValidationError, DOMAIN : Any, EXTERNAL : Any> :
    Externalizer<DOMAIN, EXTERNAL>,
    Verifier<ERROR, EXTERNAL, DOMAIN>

interface Externalizer<DOMAIN : Any, EXTERNAL : Any> {
    fun DOMAIN.externalize(): EXTERNAL
}

interface Internalizer<EXTERNAL : Any, DOMAIN : Any> {
    fun EXTERNAL.internalize(): DOMAIN
}

interface Verifier<ERROR : ValidationError, EXTERNAL : Any, DOMAIN : Any> : Internalizer<EXTERNAL, EitherNel<ERROR, DOMAIN>> {
    fun EXTERNAL.verify() = internalize().mapLeft(::ValidationErrors)
}
