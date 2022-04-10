package io.amkrosa.rest.controller

import com.sendgrid.Request
import com.sendgrid.Response
import io.micronaut.email.AsyncEmailSender
import io.micronaut.email.BodyType
import io.micronaut.email.Email
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Post
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import org.reactivestreams.Publisher
import org.slf4j.LoggerFactory
import reactor.core.publisher.Mono

@Secured(SecurityRule.IS_ANONYMOUS)
@Controller("/emailMicroservice")
class EmailController(
    private val emailSender: AsyncEmailSender<Request, Response>
    ) {

    @Get(uri="/", produces=["text/plain"])
    fun index(): String {
        return "Example Response"
    }

    //test endpoint
    @Post("/send")
    fun send(@Body("to") to: String): Publisher<HttpResponse<*>> {
        return Mono.from(emailSender.sendAsync(
            Email.builder()
            .to(to)
            .subject("Sending email with Twilio Sendgrid is Fun")
            .body("and <em>easy</em> to do anywhere with <strong>Micronaut Email</strong>", BodyType.HTML)))
            .doOnNext { rsp: Response ->
                LOG.info("response status {}\nresponse body {}\nresponse headers {}",
                    rsp.statusCode, rsp.body, rsp.headers)
            }.map { rsp: Response ->
                if (rsp.statusCode >= 400) HttpResponse.unprocessableEntity() else HttpResponse.accepted<Any>()
            }
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(EmailController::class.java)
    }
}