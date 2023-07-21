package com.example.pricehunter.data.remote

import com.example.pricehunter.data.remote.model.SampleDTO
import retrofit2.Response
import retrofit2.http.GET

interface ApiServices {
    companion object {
        const val BASE_URL = "https://sample.url.com"
        const val TIMEOUT: Long = 5
    }

    @GET("sample_route/")
    suspend fun getSampleData(): Response<SampleDTO?>
}