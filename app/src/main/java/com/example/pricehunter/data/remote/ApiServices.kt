package com.example.pricehunter.data.remote

import com.example.pricehunter.data.remote.model.SampleDTO
import com.example.pricehunter.data.remote.model.SearchResultDTO
import com.example.pricehunter.domain.model.Image
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiServices {
    companion object {
        const val BASE_URL = "https://api.ebay.com/"
        const val TIMEOUT: Long = 5
    }

    @POST("buy/browse/v1/item_summary/search_by_image?&limit=10&sort=-price")
    suspend fun searchByImage(@Body image: Image): Response<SearchResultDTO?>

    @GET("sample_route/")
    suspend fun getSampleData(): Response<SampleDTO?>
}