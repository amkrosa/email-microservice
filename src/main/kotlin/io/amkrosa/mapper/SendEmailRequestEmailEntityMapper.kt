package io.amkrosa.mapper

import io.amkrosa.model.dto.SendEmailTemplateRequest
import io.amkrosa.model.entity.Email
import io.amkrosa.util.VelocityUtil
import io.micronaut.views.velocity.VelocityFactory
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA)
abstract class SendEmailRequestEmailEntityMapper {

    var velocityUtil: VelocityUtil = VelocityUtil(VelocityFactory())

    fun sendEmailTemplateRequestToEmail(sendEmailTemplateRequest: SendEmailTemplateRequest): Email {
        val html: String = velocityUtil.templateAsString(
            sendEmailTemplateRequest.template,
            sendEmailTemplateRequest.attributes
        )
        return Email(sendEmailTemplateRequest.toEmail!!, sendEmailTemplateRequest.subject!!, html)
    }
}