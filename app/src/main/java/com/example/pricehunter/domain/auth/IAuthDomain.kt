package com.example.pricehunter.domain.auth

import com.example.pricehunter.data.Resource
import com.example.pricehunter.domain.model.AccessToken
import com.example.pricehunter.domain.model.RefreshToken

interface IAuthDomain {
    suspend fun getAccessToken(authCode: String, redirectUri: String): Resource<AccessToken>
    suspend fun getRefreshAccessToken(refreshToken: String, scope: String): Resource<RefreshToken>
}