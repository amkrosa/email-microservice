package io.amkrosa.mapper

import io.amkrosa.model.dto.GetEmailResponse
import io.amkrosa.model.entity.Email
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper
abstract class GetEmailEmailEntityMapper {

    @Mapping(source = "uuid", target = "emailUuid")
    @Mapping(source = "toEmail", target = "toEmail")
    @Mapping(source = "subject", target = "subject")
    @Mapping(source = "dateTimeSent", target = "dateTimeSent")
    @Mapping(source = "html", target = "html")
    abstract fun emailEntityToGetEmailResponse(email: Email): GetEmailResponse
}