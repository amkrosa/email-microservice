package io.amkrosa.model.entity

import java.time.LocalDateTime
import java.util.*
import javax.annotation.processing.Generated
import javax.persistence.*

@Entity
class Email (
    var toEmail: String,
    var subject: String,
    @Lob
    @Column
    var html: String
){
    @Id
    val uuid: UUID = UUID.randomUUID()
    val dateTimeSent: LocalDateTime = LocalDateTime.now()
}