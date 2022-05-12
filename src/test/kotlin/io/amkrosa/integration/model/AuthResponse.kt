package io.amkrosa.integration.model

import com.fasterxml.jackson.annotation.JsonProperty

data class AuthResponse(
    val username: String,
    @JsonProperty("access_token")
    val accessToken: String,
    @JsonProperty("token_type")
    val tokenType: String,
    val expiresIn: Int
) {
    fun bearerToken(): String {
        return "${tokenType} ${accessToken}"
    }
}