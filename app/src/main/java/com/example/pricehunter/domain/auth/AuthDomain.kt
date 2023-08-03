package com.example.pricehunter.domain.auth

import com.example.pricehunter.base.BaseDomain
import com.example.pricehunter.data.ItemWrapper
import com.example.pricehunter.data.Resource
import com.example.pricehunter.data.remote.AuthApiServices
import com.example.pricehunter.domain.model.AccessToken
import com.example.pricehunter.domain.model.RefreshToken
import javax.inject.Inject

class AuthDomain @Inject constructor(private val authApiServices: AuthApiServices) : BaseDomain(),
    IAuthDomain {
    override suspend fun getAccessToken(
        authCode: String,
        redirectUri: String
    ): Resource<AccessToken> {
        return handleApiResponse {
            val response =
                authApiServices.getAccessToken(code = authCode, redirectUri = redirectUri)
            ItemWrapper(response.body()?.toAccessToken(), response)
        }
    }

    override suspend fun getRefreshAccessToken(
        refreshToken: String,
        scope: String
    ): Resource<RefreshToken> {
        return handleApiResponse {
            val response =
                authApiServices.getRefreshAccessToken(refreshToken = refreshToken, scope = scope)
            ItemWrapper(response.body()?.toRefreshToken(), response)
        }
    }
}
