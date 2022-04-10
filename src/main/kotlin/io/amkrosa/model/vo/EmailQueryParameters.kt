package io.amkrosa.model.vo

import java.time.LocalDate

data class EmailQueryParameters(val to: String, val topic: String, val dateSent: LocalDate)
