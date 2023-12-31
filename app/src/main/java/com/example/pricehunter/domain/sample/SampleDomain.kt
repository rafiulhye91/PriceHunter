package com.example.pricehunter.domain.sample

import com.example.pricehunter.base.BaseDomain
import com.example.pricehunter.data.ItemWrapper
import com.example.pricehunter.data.Resource
import com.example.pricehunter.data.local.DaoServices
import com.example.pricehunter.data.remote.ApiServices
import com.example.pricehunter.domain.model.SampleDomainData
import javax.inject.Inject

class SampleDomain @Inject constructor(
    private val apiServices: ApiServices,
    private val daoServices: DaoServices
) : BaseDomain(), ISampleDomain {

    override suspend fun getSampleData(): Resource<SampleDomainData?> {
        return handleApiResponse {
            val response = apiServices.getSampleData()
            ItemWrapper(response.body()?.toSampleDomainData(), response)
        }
    }

    override suspend fun getAllSampleData(): Resource<List<SampleDomainData?>> {
        return handleDbResponse {
            daoServices.getAllSampleDbData().map { it.toSampleDomainData() }
        }
    }

}