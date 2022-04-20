package io.amkrosa.util

import io.amkrosa.service.SendgridEmailService
import io.micronaut.views.velocity.VelocityFactory
import jakarta.inject.Singleton
import org.apache.velocity.VelocityContext
import java.io.StringWriter

@Singleton
class VelocityUtil(
    private val velocityFactory: VelocityFactory
) {
    fun templateAsString(templateName: String, model: Map<String, Any>): String {
        val writer = StringWriter()
        val velocityContext = VelocityContext(model)
        velocityFactory.velocityEngine.mergeTemplate("views/${templateName}.vm", "UTF-8", velocityContext, writer)
        return writer.toString()
    }
}