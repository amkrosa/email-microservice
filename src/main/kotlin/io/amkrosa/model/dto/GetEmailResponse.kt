package io.amkrosa.model.dto

import java.time.LocalDate

//TODO - think of method to store html
data class GetEmailResponse(val from: String, val to: String, val date: LocalDate, val html: String)
