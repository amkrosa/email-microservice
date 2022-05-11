package io.amkrosa.repository

import io.amkrosa.model.entity.EmailUser
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.reactive.ReactorCrudRepository
import reactor.core.publisher.Mono
import java.util.*

@Repository
interface EmailUserRepository : ReactorCrudRepository<EmailUser, UUID> {
    fun findByName(name: String): Mono<EmailUser>
}