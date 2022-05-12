package io.amkrosa.client.email

import io.micronaut.context.annotation.Requires
import io.micronaut.email.Email
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.simple.SimpleHttpResponseFactory
import jakarta.inject.Singleton
import reactor.core.publisher.Mono

@Requires(env = ["mock"])
@Singleton
class EmailSenderMock<REQ : HttpRequest<*>, RES : HttpResponse<*>> : EmailSender<REQ, RES> {
    override fun send(emailBuilder: Email.Builder): Mono<HttpResponse<*>> {
        return Mono.just(SimpleHttpResponseFactory.INSTANCE.ok<RES>())
    }
}