package com.example.pricehunter.data.remote.model

import com.example.pricehunter.domain.model.AccessToken
import com.google.gson.annotations.SerializedName

data class AccessTokenDTO(
    @SerializedName("access_token")
    val token: String,
    @SerializedName("expires_in")
    val expiredIn: Int,
    @SerializedName("refresh_token")
    val refreshToken: String,
    @SerializedName("refresh_token_expires_in")
    val refreshTokenExpiredIn: Int,
    @SerializedName("token_type")
    val tokenType: String
) {
    fun toAccessToken(): AccessToken {
        return AccessToken(
            token = token,
            expiredIn = expiredIn
        )
    }

}