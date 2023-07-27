package com.example.pricehunter.domain.auth

import com.example.pricehunter.data.Resource
import com.example.pricehunter.domain.model.AccessToken

interface IAuthDomain {
    suspend fun getAccessToken(authCode: String, redirectUri: String): Resource<AccessToken>
}