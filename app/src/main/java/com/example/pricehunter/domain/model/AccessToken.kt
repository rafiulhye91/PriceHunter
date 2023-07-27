package com.example.pricehunter.domain.model

data class AccessToken(
    val token: String,
    val expiredIn: Int
)
