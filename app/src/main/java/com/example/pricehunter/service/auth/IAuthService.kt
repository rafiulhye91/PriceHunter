package com.example.pricehunter.service.auth

import com.example.pricehunter.data.Resource
import com.example.pricehunter.domain.model.AccessToken

interface IAuthService {
    suspend fun getAccessToken(authCode: String, redirectUri: String): Resource<AccessToken>
}