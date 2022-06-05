package io.amkrosa.client.email

import com.mailjet.client.MailjetResponse
import io.micronaut.context.annotation.Requires
import io.micronaut.email.AsyncEmailSender
import io.micronaut.email.Email
import io.micronaut.http.HttpRequest
import jakarta.inject.Singleton
import reactor.core.publisher.Mono

@Requires(notEnv = ["mock"])
@Singleton
class EmailSenderImpl<REQ : HttpRequest<*>, RES : MailjetResponse>(private val emailSender: AsyncEmailSender<REQ, RES>) :
    EmailSender<REQ, RES> {
    override fun send(emailBuilder: Email.Builder): Mono<MailjetResponse> {
        return Mono.from(emailSender.sendAsync(emailBuilder))
    }
}