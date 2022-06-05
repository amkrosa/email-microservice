package io.amkrosa.kafka


//@KafkaListener(offsetReset = OffsetReset.EARLIEST)
//class ProductListener(val emailRepository: EmailRepository) {
//
//    @Topic("EmailTopic")
//    fun receive(email: Email) {
//        emailRepository.save(email).block()
//        LOG.info("Got Email: ${email.uuid} ${email.subject} ${email.dateTimeSent}")
//    }
//
//    companion object {
//        private val LOG = LoggerFactory.getLogger(EmailController::class.java)
//    }
//}