package com.example.pricehunter.domain.model

data class AccessToken(
    val token: String,
    val expiredIn: Long,
    val refreshToken: String,
    val refreshTokenExpiredIn: Long
)
