package io.amkrosa.client.email

import io.micronaut.context.annotation.Requires
import io.micronaut.email.AsyncEmailSender
import io.micronaut.email.Email
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import jakarta.inject.Singleton
import reactor.core.publisher.Mono

@Requires(notEnv = ["mock"])
@Singleton
class EmailSenderImpl<REQ : HttpRequest<*>, RES : HttpResponse<*>>(private val emailSender: AsyncEmailSender<REQ, RES>) :
    EmailSender<REQ, RES> {
    override fun send(emailBuilder: Email.Builder): Mono<HttpResponse<*>> {
        return Mono.from(emailSender.sendAsync(emailBuilder))
    }
}