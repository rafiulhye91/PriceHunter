package com.example.pricehunter.domain.finder

import com.example.pricehunter.data.Resource
import com.example.pricehunter.domain.model.Image
import com.example.pricehunter.domain.model.Item

interface IFinderDomain {
    suspend fun searchByImage(accessToken: String, image: Image): Resource<List<Item>>
}