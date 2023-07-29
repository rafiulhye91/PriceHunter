package com.example.pricehunter.data.remote.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class AuthBearerInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("X-EBAY-C-MARKETPLACE-ID", "application/x-www-form-urlencoded")
            .addHeader("Content-Type", "application/json")
            .build()
        return chain.proceed(request)
    }
}