package io.amkrosa.integration

import io.amkrosa.integration.client.EmailClient
import io.amkrosa.model.dto.GetEmailResponse
import io.amkrosa.model.dto.SendEmailTemplateRequest
import io.amkrosa.model.dto.Template
import io.amkrosa.rest.controller.EmailController
import io.amkrosa.util.VelocityUtil
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.slf4j.LoggerFactory

@MicronautTest(environments = ["mock"], packages = ["io.amkrosa"])
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ApiTest {

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
            .element(response?.size?.minus(1) ?: 0)
            .extracting("subject", "toEmail", "html")
            .contains(
                sendEmailTemplateRequest.subject,
                sendEmailTemplateRequest.toEmail,
                getHtmlFromSendEmailTemplateRequest(sendEmailTemplateRequest)
            )
    }

    @ParameterizedTest
    @MethodSource("invalidData")
    fun shouldNotSendEmailWithInvalidData(subject: String?, toEmail: String?, template: String?) {
        //given
        val sendEmailTemplateRequest = SendEmailTemplateRequest(null, toEmail, template, null)

        //when & then
        Assertions.assertThatThrownBy { emailClient.sendEmailWithProblem(sendEmailTemplateRequest) }
            .hasMessageContainingAll(
                "\"send.sendEmailTemplateRequest.template\"",
                "\"send.sendEmailTemplateRequest.toEmail\"",
                "\"send.sendEmailTemplateRequest.subject\""
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

        @JvmStatic
        fun invalidData() = listOf(
            Arguments.of("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "annakrasowska2gmail", "INVALID_TEMPLATE"),
            Arguments.of(" ", " ", " "),
            Arguments.of(null, null, null)
        )
    }
}