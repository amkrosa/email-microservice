package io.amkrosa.rest.controller

import io.amkrosa.model.dto.SendEmailTemplateRequest
import io.amkrosa.rest.api.EmailApi
import io.amkrosa.service.EmailService
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import org.reactivestreams.Publisher
import org.slf4j.LoggerFactory
import javax.transaction.Transactional

@Secured(SecurityRule.IS_ANONYMOUS)
@Controller("/email")
class EmailController(
    private val emailService: EmailService
) : EmailApi {

    @Post("/send")
    override fun send(@Body sendEmailTemplateRequest: SendEmailTemplateRequest): Publisher<HttpResponse<*>> {
        return emailService.sendTemplateEmail(sendEmailTemplateRequest)
    }

    @Get("/all")
    override fun getAll(): Publisher<HttpResponse<*>> {
        return emailService.getAllEmails()
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(EmailController::class.java)
    }
}