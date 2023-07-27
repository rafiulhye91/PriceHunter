package com.example.pricehunter.service.auth

import com.example.pricehunter.data.Resource
import com.example.pricehunter.domain.auth.IAuthDomain
import com.example.pricehunter.domain.model.AccessToken
import javax.inject.Inject

class AuthService @Inject constructor(private val domain: IAuthDomain) : IAuthService {
    override suspend fun getAccessToken(
        authCode: String,
        redirectUri: String
    ): Resource<AccessToken> {
        return domain.getAccessToken(authCode, redirectUri)
    }

}