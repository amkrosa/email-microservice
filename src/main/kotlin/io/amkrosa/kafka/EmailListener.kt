package io.amkrosa.kafka

import io.amkrosa.model.entity.Email
import io.amkrosa.repository.EmailRepository
import io.amkrosa.rest.controller.EmailController
import io.micronaut.configuration.kafka.annotation.KafkaListener
import io.micronaut.configuration.kafka.annotation.OffsetReset
import io.micronaut.configuration.kafka.annotation.Topic
import org.slf4j.LoggerFactory


@KafkaListener(offsetReset = OffsetReset.EARLIEST)
class ProductListener(val emailRepository: EmailRepository) {

    @Topic("EmailTopic")
    fun receive(email: Email) {
        emailRepository.save(email).block()
        LOG.info("Got Email: ${email.uuid} ${email.subject} ${email.dateTimeSent}")
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(EmailController::class.java)
    }
}