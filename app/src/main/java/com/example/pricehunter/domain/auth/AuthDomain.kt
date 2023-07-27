package com.example.pricehunter.domain.auth

import com.example.pricehunter.base.BaseDomain
import com.example.pricehunter.data.ItemWrapper
import com.example.pricehunter.data.Resource
import com.example.pricehunter.data.remote.ApiServices
import com.example.pricehunter.domain.model.AccessToken
import javax.inject.Inject

class AuthDomain @Inject constructor(private val apiServices: ApiServices) : BaseDomain(),
    IAuthDomain {
    override suspend fun getAccessToken(
        authCode: String,
        redirectUri: String
    ): Resource<AccessToken> {
        return handleApiResponse {
            val response = apiServices.getAccessToken(code = authCode, redirectUri = redirectUri)
            ItemWrapper(response.body()?.toAccessToken(), response)
        }
    }
}
