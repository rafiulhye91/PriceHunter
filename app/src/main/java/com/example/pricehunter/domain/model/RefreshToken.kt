package com.example.pricehunter.domain.model

data class RefreshToken(
    val accessToken: String,
    val expiredIn: Long
)
