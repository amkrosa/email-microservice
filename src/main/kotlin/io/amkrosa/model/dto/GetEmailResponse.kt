package io.amkrosa.model.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime
import java.util.*

data class GetEmailResponse(
    val emailUuid: UUID, val subject: String, val toEmail: String,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    val dateTimeSent: LocalDateTime,
    val html: String
)
