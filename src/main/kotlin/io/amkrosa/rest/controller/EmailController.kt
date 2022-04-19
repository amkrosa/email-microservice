package io.amkrosa.rest.controller

import io.amkrosa.model.dto.SendEmailTemplateRequest
import io.amkrosa.rest.api.EmailApi
import io.amkrosa.service.EmailService
import io.micronaut.http.HttpResponse
import org.reactivestreams.Publisher
import org.slf4j.LoggerFactory

class EmailController(
    private val emailService: EmailService
) : EmailApi {

    override fun send(sendEmailTemplateRequest: SendEmailTemplateRequest): Publisher<HttpResponse<*>> {
        return emailService.sendTemplateEmail(sendEmailTemplateRequest)
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(EmailController::class.java)
    }
}