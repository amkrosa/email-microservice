package io.amkrosa.mapper

import org.mapstruct.factory.Mappers

object Mappers {
    val sendEmailRequestEmailEntityMapper: SendEmailRequestEmailEntityMapper = Mappers.getMapper(SendEmailRequestEmailEntityMapper::class.java)
}