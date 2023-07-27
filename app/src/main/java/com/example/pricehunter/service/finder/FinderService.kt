package com.example.pricehunter.service.finder

import com.example.pricehunter.data.Resource
import com.example.pricehunter.domain.finder.IFinderDomain
import com.example.pricehunter.domain.model.Image
import com.example.pricehunter.domain.model.Item
import javax.inject.Inject

class FinderService @Inject constructor(private val domain: IFinderDomain) : IFinderService {
    override suspend fun searchByImage(image: Image): Resource<List<Item>> {
        return domain.searchByImage(image)
    }
}