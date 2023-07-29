package com.example.pricehunter.data.remote

import com.example.pricehunter.data.remote.model.AccessTokenDTO
import com.example.pricehunter.data.remote.model.RefreshTokenDTO
import com.example.pricehunter.data.remote.model.SampleDTO
import retrofit2.Response
import retrofit2.http.*

interface AuthApiServices {
    companion object {
        const val BASE_URL = "https://api.ebay.com"
        const val TIMEOUT: Long = 5
    }

    @FormUrlEncoded
    @POST("identity/v1/oauth2/token")
    suspend fun getAccessToken(
        @Field("grant_type") grantType: String = "authorization_code",
        @Field("code") code: String,
        @Field("redirect_uri") redirectUri: String,
    ): Response<AccessTokenDTO?>

    @FormUrlEncoded
    @POST("identity/v1/oauth2/token")
    suspend fun getRefreshAccessToken(
        @Field("grant_type") grantType: String = "refresh_token",
        @Field("refresh_token") refreshToken: String,
        @Field("scope") scope: String
    ): Response<RefreshTokenDTO?>

    @GET("sample_route/")
    suspend fun getSampleData(): Response<SampleDTO?>
}