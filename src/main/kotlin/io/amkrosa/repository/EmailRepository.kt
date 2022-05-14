package io.amkrosa.repository

import io.amkrosa.model.entity.Email
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.reactive.ReactorCrudRepository
import reactor.core.publisher.Flux
import java.util.*

@Repository
interface EmailRepository : ReactorCrudRepository<Email, UUID> {
    fun listOrderByDateTimeSentDesc(): Flux<Email>
}