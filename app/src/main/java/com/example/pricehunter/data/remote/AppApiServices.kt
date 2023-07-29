package com.example.pricehunter.data.remote

import com.example.pricehunter.data.remote.model.SearchResultDTO
import com.example.pricehunter.domain.model.Image
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AppApiServices {
    companion object {
        const val BASE_URL = "https://api.ebay.com"
        const val TIMEOUT: Long = 10
    }

    @POST("buy/browse/v1/item_summary/search_by_image?&limit=10&sort=-price")
    suspend fun searchByImage(
        @Header("Authorization") accessToken: String,
        @Body image: Image
    ): Response<SearchResultDTO?>

}