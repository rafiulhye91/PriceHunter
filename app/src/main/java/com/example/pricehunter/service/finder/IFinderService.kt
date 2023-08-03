package com.example.pricehunter.service.finder

import com.example.pricehunter.data.Resource
import com.example.pricehunter.domain.model.ImageRequest
import com.example.pricehunter.domain.model.Item


interface IFinderService {
    suspend fun searchByImage(accessToken: String, imageRequest: ImageRequest): Resource<List<Item>>
}