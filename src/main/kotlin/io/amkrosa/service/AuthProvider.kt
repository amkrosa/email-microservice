package io.amkrosa.service

import io.amkrosa.repository.EmailUserRepository
import io.micronaut.http.HttpRequest
import io.micronaut.security.authentication.AuthenticationFailureReason
import io.micronaut.security.authentication.AuthenticationProvider
import io.micronaut.security.authentication.AuthenticationRequest
import io.micronaut.security.authentication.AuthenticationResponse
import jakarta.inject.Singleton
import org.reactivestreams.Publisher
import reactor.core.publisher.Flux
import reactor.core.publisher.FluxSink

@Singleton
class AuthProvider(private val emailUserRepository: EmailUserRepository) : AuthenticationProvider {

    override fun authenticate(
        httpRequest: HttpRequest<*>?,
        authenticationRequest: AuthenticationRequest<*, *>
    ): Publisher<AuthenticationResponse> {
        return Flux.create({ emitter: FluxSink<AuthenticationResponse> ->
            val user = emailUserRepository.findByName(authenticationRequest.identity as String).blockOptional()
            if (user.isEmpty) {
                emitter.next(AuthenticationResponse.failure(AuthenticationFailureReason.USER_NOT_FOUND))
                emitter.complete()
            }
            if (authenticationRequest.secret == user.get().password) emitter.next(
                AuthenticationResponse.success(
                    authenticationRequest.identity as String
                )
            )
            else emitter.next(AuthenticationResponse.failure(AuthenticationFailureReason.CREDENTIALS_DO_NOT_MATCH))
            emitter.complete()
        }, FluxSink.OverflowStrategy.LATEST)
    }
}