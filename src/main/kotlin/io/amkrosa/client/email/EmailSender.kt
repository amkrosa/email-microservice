package io.amkrosa.client.email

import io.micronaut.email.Email
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import reactor.core.publisher.Mono

interface EmailSender<REQ : HttpRequest<*>, RES : HttpResponse<*>> {
    fun send(emailBuilder: Email.Builder): Mono<HttpResponse<*>>
}