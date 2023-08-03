package com.example.pricehunter.domain.finder

import com.example.pricehunter.base.BaseDomain
import com.example.pricehunter.data.ItemWrapper
import com.example.pricehunter.data.Resource
import com.example.pricehunter.data.remote.AppApiServices
import com.example.pricehunter.domain.model.ImageRequest
import com.example.pricehunter.domain.model.Item
import javax.inject.Inject

class FinderDomain @Inject constructor(
    private val appApiServices: AppApiServices,
) : BaseDomain(), IFinderDomain {

    override suspend fun searchByImage(
        accessToken: String,
        imageRequest: ImageRequest
    ): Resource<List<Item>> {
        return handleApiResponse {
            val response = appApiServices.searchByImage(accessToken, imageRequest)
            ItemWrapper(response.body()?.itemSummaries?.map { it.toItem() }, response = response)
        }
    }
}