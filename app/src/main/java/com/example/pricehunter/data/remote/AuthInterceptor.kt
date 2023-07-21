package com.example.pricehunter.data.remote

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(private val apiKey: String) : Interceptor {
    companion object {
        private const val KEY_ID = "api_key_id"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalHttpUrl = original.url

        val url = originalHttpUrl.newBuilder()
            .addQueryParameter(KEY_ID, apiKey)
            .build()

        val requestBuilder = original.newBuilder()
            .url(url)

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}
