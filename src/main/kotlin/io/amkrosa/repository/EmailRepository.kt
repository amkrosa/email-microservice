package io.amkrosa.repository

import io.amkrosa.model.entity.Email
import io.micronaut.data.annotation.Repository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.CrudRepository
import io.micronaut.data.repository.reactive.ReactorCrudRepository
import java.util.*

@Repository
interface EmailRepository : CrudRepository<Email, UUID> {
}