package io.amkrosa.integration

import io.amkrosa.integration.client.AuthClient
import io.amkrosa.integration.client.EmailClient
import io.amkrosa.model.entity.EmailUser
import io.amkrosa.repository.EmailUserRepository
import io.amkrosa.rest.controller.EmailController
import io.micronaut.context.annotation.Property
import io.micronaut.security.authentication.UsernamePasswordCredentials
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.slf4j.LoggerFactory

@MicronautTest(environments = ["mock"], packages = ["io.amkrosa"])
@Property(name = "micronaut.security.enabled", value = "true")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AuthTest {

    @Inject
    lateinit var emailClient: EmailClient

    @Inject
    lateinit var authClient: AuthClient

    @Inject
    lateinit var emailUserRepository: EmailUserRepository

    @Test
    fun shouldLoginWithProperPassword() {
        //given
        emailUserRepository.save(EmailUser(CORRECT_USER, CORRECT_PASSWORD)).block()

        //when
        val response = authClient.getToken(UsernamePasswordCredentials(CORRECT_USER, CORRECT_PASSWORD))?.block()

        //then
        Assertions.assertThat(response)
            .extracting("username", "tokenType")
            .containsExactly(CORRECT_USER, TOKEN_TYPE)
    }

    @ParameterizedTest
    @MethodSource("invalidData")
    fun shouldNotLoginWithInvalidPassword(username: String, password: String, error: String) {
        //given
        emailUserRepository.save(EmailUser(CORRECT_USER, CORRECT_PASSWORD)).block()

        //when & then
        Assertions.assertThatThrownBy {
            authClient.getTokenWithProblem(
                UsernamePasswordCredentials(
                    username,
                    password
                )
            )
        }
            .hasMessage(error)
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(EmailController::class.java)

        val CORRECT_USER: String = "anna"
        val CORRECT_PASSWORD: String = "password"
        val INCORRECT_USER: String = "annaa"
        val INCORRECT_PASSWORD: String = "passs"
        val TOKEN_TYPE: String = "Bearer"
        val USER_NOT_FOUND: String = "User Not Found"
        val CREDENTIALS_DO_NOT_MATCH: String = "Credentials Do Not Match"

        @JvmStatic
        fun invalidData() = listOf(
            Arguments.of(CORRECT_USER, INCORRECT_PASSWORD, CREDENTIALS_DO_NOT_MATCH),
            Arguments.of(INCORRECT_USER, CORRECT_PASSWORD, USER_NOT_FOUND)
        )
    }
}