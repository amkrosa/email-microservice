package io.amkrosa.model.dto

import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

@Introspected
data class SendEmailTemplateRequest(
    @field:NotBlank
    @field:Min(1)
    @field:Max(40)
    val subject: String?,
    @field:NotBlank
    @field:Pattern(regexp = "^[a-zA-Z0-9_!#\$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+\$")
    val toEmail: String?,
    @field:NotBlank
    @field:Pattern(regexp = Template.REGISTRATION_CONST)
    val template: String?,
    val attributes: Map<String, String>?
) {
}