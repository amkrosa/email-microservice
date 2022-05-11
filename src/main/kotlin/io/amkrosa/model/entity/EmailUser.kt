package io.amkrosa.model.entity

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class EmailUser(
    val name: String,
    val password: String,
    @Id
    val uuid: UUID = UUID.randomUUID()
) {
}