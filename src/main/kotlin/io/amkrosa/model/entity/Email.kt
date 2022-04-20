package io.amkrosa.model.entity

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
}