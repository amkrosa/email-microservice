package io.amkrosa.model.dto

import java.time.LocalDateTime
import java.util.*

data class GetEmailResponse(
    val emailUuid: UUID, val subject: String, val toEmail: String,
    val dateTimeSent: LocalDateTime, val html: String
)
