package com.example.pricehunter.service.auth

import com.example.pricehunter.data.Resource
import com.example.pricehunter.domain.model.AccessToken
import com.example.pricehunter.domain.model.RefreshToken

interface IAuthService {
    suspend fun getAccessToken(authCode: String, redirectUri: String): Resource<AccessToken>
    suspend fun getRefreshAccessToken(refreshToken: String, scope: String): Resource<RefreshToken>
}