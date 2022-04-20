package io.amkrosa.service

import io.amkrosa.model.dto.SendEmailTemplateRequest
import io.amkrosa.model.vo.EmailQueryParameters
import io.micronaut.http.HttpResponse
import org.reactivestreams.Publisher
import java.util.*

interface EmailService {
    fun sendTemplateEmail(sendEmailTemplateRequest: SendEmailTemplateRequest): Publisher<HttpResponse<*>>
    fun getAllEmails(): Publisher<HttpResponse<*>>
    fun getQueriedEmails(emailQueryParameters: EmailQueryParameters): Publisher<HttpResponse<*>>
    fun getEmail(emailUuid: UUID): Publisher<HttpResponse<*>>
}