package io.amkrosa.integration

import io.amkrosa.integration.client.EmailClient
import io.amkrosa.model.dto.GetEmailResponse
import io.amkrosa.model.dto.GetEmailsResponse
import io.amkrosa.model.dto.SendEmailTemplateRequest
import io.amkrosa.model.dto.Template
import io.amkrosa.rest.controller.EmailController
import io.amkrosa.util.VelocityUtil
import io.micronaut.http.HttpRequest
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import net.bytebuddy.asm.Advice
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.slf4j.LoggerFactory
import java.time.LocalDateTime
import java.util.*

@MicronautTest(packages = ["io.amkrosa"])
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ApiIT {

    private val EMAIL_REQUEST_FIELDS = object {
        val SUBJECT: String = "Test"
        val TO_EMAIL: String = "annakrasowska2@gmail.com"
        val TEMPLATE: Template = Template.REGISTRATION
        val ATTRIBUTES = mapOf("username" to "Anna")
    }

    @Inject
    lateinit var emailClient: EmailClient

    @Inject
    lateinit var velocityUtil: VelocityUtil

    val sendEmailTemplateRequest = sampleSendEmailTemplateRequest(1)

    @BeforeAll
    fun setup() {
        emailClient.sendEmail(sendEmailTemplateRequest)
    }

    @Test
    fun shouldSendEmail() {
        //when
        emailClient.sendEmail(sampleSendEmailTemplateRequest(2))

        //then
        val emails = emailClient.getAllEmails()?.block()?.emails
        Assertions.assertThat(emails)
            .hasSize(2)
            .allMatch {
                it.subject.contains(EMAIL_REQUEST_FIELDS.SUBJECT) && it.toEmail == EMAIL_REQUEST_FIELDS.TO_EMAIL &&
                        it.html == getHtmlFromSendEmailTemplateRequest(sendEmailTemplateRequest)
            }
    }


    @Test
    fun shouldGetAllEmails() {
        //when
        val response = emailClient.getAllEmails()?.block()?.emails

        //then
        Assertions.assertThat(response)
            .isNotEmpty()
            .hasOnlyElementsOfType(GetEmailResponse::class.java)
            .element(0)
            .extracting("subject", "toEmail", "html")
            .contains(
                sendEmailTemplateRequest.subject,
                sendEmailTemplateRequest.toEmail,
                getHtmlFromSendEmailTemplateRequest(sendEmailTemplateRequest)
            )
    }

    private fun sampleSendEmailTemplateRequest(i: Int): SendEmailTemplateRequest {
        return SendEmailTemplateRequest(
            subject = "${EMAIL_REQUEST_FIELDS.SUBJECT} $i",
            toEmail = EMAIL_REQUEST_FIELDS.TO_EMAIL,
            template = EMAIL_REQUEST_FIELDS.TEMPLATE.name,
            attributes = EMAIL_REQUEST_FIELDS.ATTRIBUTES
        )
    }

    private fun getHtmlFromSendEmailTemplateRequest(sendEmailTemplateRequest: SendEmailTemplateRequest): String {
        return velocityUtil.templateAsString(
            sendEmailTemplateRequest.template,
            sendEmailTemplateRequest.attributes
        )
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(EmailController::class.java)
    }
}