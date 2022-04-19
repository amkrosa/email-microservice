package io.amkrosa.service

import io.amkrosa.model.dto.SendEmailTemplateRequest
import io.micronaut.http.HttpResponse
import org.reactivestreams.Publisher

interface EmailService {
    fun sendTemplateEmail(sendEmailTemplateRequest: SendEmailTemplateRequest): Publisher<HttpResponse<*>>
}