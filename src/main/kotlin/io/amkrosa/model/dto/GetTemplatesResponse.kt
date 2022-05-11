package io.amkrosa.model.dto

data class GetTemplatesResponse(val templates: List<String> = Template.values().map { it.name }) {
}