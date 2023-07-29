package com.example.pricehunter.domain.finder

import com.example.pricehunter.base.BaseDomain
import com.example.pricehunter.data.ItemWrapper
import com.example.pricehunter.data.Resource
import com.example.pricehunter.data.remote.AppApiServices
import com.example.pricehunter.domain.model.Image
import com.example.pricehunter.domain.model.Item
import javax.inject.Inject

class FinderDomain @Inject constructor(
    private val appApiServices: AppApiServices,
) : BaseDomain(), IFinderDomain {

    override suspend fun searchByImage(accessToken: String, image: Image): Resource<List<Item>> {
        return handleApiResponse {
            val response = appApiServices.searchByImage(accessToken, image)
            ItemWrapper(response.body()?.itemSummaries?.map { it.toItem() }, response = response)
        }
    }
}