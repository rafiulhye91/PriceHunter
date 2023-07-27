package com.example.pricehunter.data.remote

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(private val credentials: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", credentials)
            .addHeader("Content-Type", "application/x-www-form-urlencoded")
            .addHeader("Cache-Control", "no-cache")
            .addHeader("Accept", "application/json")
            .build()
        return chain.proceed(request)
    }
}