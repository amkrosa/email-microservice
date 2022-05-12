package io.amkrosa.integration.client

import io.amkrosa.model.dto.GetEmailsResponse
import io.amkrosa.model.dto.SendEmailTemplateRequest
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Header
import io.micronaut.http.annotation.Post
import io.micronaut.http.client.annotation.Client
import org.zalando.problem.Problem
import reactor.core.publisher.Mono

@Client("/")
interface EmailClient {

    @Get("/email/all")
    fun getAllEmails(): Mono<GetEmailsResponse?>?

    @Get("/email/all")
    fun getAllEmailsWithAuth(@Header(name = "Authorization") authorization: String)

    @Post("/email/send")
    fun sendEmail(@Body sendEmailTemplateRequest: SendEmailTemplateRequest)

    @Post("/email/send")
    fun sendEmailWithProblem(@Body sendEmailTemplateRequest: SendEmailTemplateRequest): Problem
}