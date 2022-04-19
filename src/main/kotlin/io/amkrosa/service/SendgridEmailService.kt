package io.amkrosa.service

import com.sendgrid.Request
import com.sendgrid.Response
import io.amkrosa.model.dto.SendEmailTemplateRequest
import io.amkrosa.rest.controller.EmailController
import io.micronaut.email.AsyncEmailSender
import io.micronaut.email.BodyType
import io.micronaut.email.Email
import io.micronaut.email.template.TemplateBody
import io.micronaut.http.HttpResponse
import io.micronaut.views.ModelAndView
import jakarta.inject.Singleton
import org.reactivestreams.Publisher
import reactor.core.publisher.Mono

@Singleton
class SendgridEmailService(
    private val emailSender: AsyncEmailSender<Request, Response>
) : EmailService {

    override fun sendTemplateEmail(sendEmailTemplateRequest: SendEmailTemplateRequest): Publisher<HttpResponse<*>> {
        return Mono.from(
            emailSender.sendAsync(
                Email.builder()
                    .to(sendEmailTemplateRequest.email)
                    .subject(sendEmailTemplateRequest.subject)
                    .body(
                        TemplateBody(
                            BodyType.HTML,
                            ModelAndView(sendEmailTemplateRequest.template.name, sendEmailTemplateRequest.attributes)
                        )
                    )
            )
        )
            .map { rsp: Response ->
                if (rsp.statusCode >= 400) HttpResponse.unprocessableEntity() else HttpResponse.accepted<Any>()
            }
    }
}