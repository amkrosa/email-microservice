package io.amkrosa.rest.api

import io.amkrosa.model.dto.SendEmailTemplateRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import org.reactivestreams.Publisher

@Secured(SecurityRule.IS_ANONYMOUS)
@Controller("/email")
interface EmailApi {
    @Post("/send")
    fun send(@Body sendEmailTemplateRequest: SendEmailTemplateRequest): Publisher<HttpResponse<*>>
}