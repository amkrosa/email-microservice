package io.amkrosa.client.email

import com.mailjet.client.MailjetResponse
import io.micronaut.email.Email
import io.micronaut.http.HttpRequest
import reactor.core.publisher.Mono

interface EmailSender<REQ : HttpRequest<*>, RES : MailjetResponse> {
    fun send(emailBuilder: Email.Builder): Mono<MailjetResponse>
}