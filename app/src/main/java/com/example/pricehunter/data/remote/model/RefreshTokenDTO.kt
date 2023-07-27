package com.example.pricehunter.data.remote.model

import com.example.pricehunter.domain.model.RefreshToken
import com.google.gson.annotations.SerializedName

data class RefreshTokenDTO(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("expires_in")
    val expiresIn: Long,
    @SerializedName("token_type")
    val tokenType: String
) {
    fun toRefreshToken(): RefreshToken {
        return RefreshToken(
            accessToken,
            expiresIn
        )
    }
}