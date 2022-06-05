package io.amkrosa.client.email

import com.mailjet.client.MailjetResponse
import io.micronaut.context.annotation.Requires
import io.micronaut.email.Email
import io.micronaut.http.HttpRequest
import jakarta.inject.Singleton
import reactor.core.publisher.Mono

@Requires(env = ["mock"])
@Singleton
class EmailSenderMock<REQ : HttpRequest<*>, RES : MailjetResponse> : EmailSender<REQ, RES> {
    override fun send(emailBuilder: Email.Builder): Mono<MailjetResponse> {
        return Mono.just(MailjetResponse(200, "{}"))
    }
}