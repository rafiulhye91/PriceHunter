package com.example.pricehunter.service.finder

import com.example.pricehunter.data.Resource
import com.example.pricehunter.domain.model.Image
import com.example.pricehunter.domain.model.Item


interface IFinderService {
    suspend fun searchByImage(image: Image): Resource<List<Item>>
}