package io.amkrosa.service

import com.sendgrid.Request
import com.sendgrid.Response
import io.amkrosa.mapper.Mappers
import io.amkrosa.model.dto.SendEmailTemplateRequest
import io.amkrosa.model.vo.EmailQueryParameters
import io.amkrosa.repository.EmailRepository
import io.amkrosa.rest.controller.EmailController
import io.amkrosa.util.VelocityUtil
import io.micronaut.email.AsyncEmailSender
import io.micronaut.email.BodyType
import io.micronaut.email.Email
import io.micronaut.email.template.TemplateBody
import io.micronaut.http.HttpResponse
import io.micronaut.views.ModelAndView
import jakarta.inject.Singleton
import org.reactivestreams.Publisher
import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*
import javax.transaction.Transactional

@Singleton
class SendgridEmailService(
    private val emailSender: AsyncEmailSender<Request, Response>,
    private val emailRepository: EmailRepository,
) : EmailService {

    override fun sendTemplateEmail(sendEmailTemplateRequest: SendEmailTemplateRequest): Publisher<HttpResponse<*>> {
        return Mono.from(
            emailSender.sendAsync(
                Email.builder()
                    .to(sendEmailTemplateRequest.toEmail)
                    .subject(sendEmailTemplateRequest.subject)
                    .body(
                        TemplateBody(
                            BodyType.HTML,
                            ModelAndView(sendEmailTemplateRequest.template.name, sendEmailTemplateRequest.attributes)
                        )
                    )
            )
        )
            .doOnSuccess {
                val email =
                    Mappers.sendEmailRequestEmailEntityMapper.sendEmailTemplateRequestToEmail(sendEmailTemplateRequest)
                emailRepository.save(email)
            }
            .map { rsp: Response ->
                if (rsp.statusCode >= 400) HttpResponse.unprocessableEntity()
                else {
                    HttpResponse.accepted<Any>()
                }
            }
    }

    override fun getAllEmails(): Publisher<HttpResponse<*>> {
        return emailRepository.findAll()
            .collectList()
            .map { emailList: List<io.amkrosa.model.entity.Email> ->
                HttpResponse.ok(emailList)
            }
    }

    override fun getQueriedEmails(emailQueryParameters: EmailQueryParameters): Publisher<HttpResponse<*>> {
        TODO("Not yet implemented")
    }

    override fun getEmail(emailUuid: UUID): Publisher<HttpResponse<*>> {
        TODO("Not yet implemented")
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(EmailController::class.java)
    }
}