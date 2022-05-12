package io.amkrosa.integration.client

import io.amkrosa.integration.model.AuthResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Post
import io.micronaut.http.client.annotation.Client
import io.micronaut.security.authentication.AuthenticationRequest
import org.zalando.problem.Problem
import reactor.core.publisher.Mono

@Client("/")
interface AuthClient {

    @Post("/login")
    fun getToken(@Body request: AuthenticationRequest<*, *>): Mono<AuthResponse?>?

    @Post("/login")
    fun getTokenWithProblem(@Body request: AuthenticationRequest<*, *>): Problem
}