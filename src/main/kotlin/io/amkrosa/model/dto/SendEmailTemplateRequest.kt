package io.amkrosa.model.dto

data class SendEmailTemplateRequest(
    val subject: String,
    val email: String,
    val template: Template,
    val attributes: Map<String, String>
) {
}