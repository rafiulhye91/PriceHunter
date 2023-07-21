package com.example.pricehunter.service.sample

import com.example.pricehunter.data.Resource
import com.example.pricehunter.domain.model.SampleDomainData
import com.example.pricehunter.domain.sample.ISampleDomain
import javax.inject.Inject

class SampleService @Inject constructor(private val domain: ISampleDomain) : ISampleService {
    override suspend fun getSampleData(): Resource<SampleDomainData?> {
        return domain.getSampleData()
    }

    override suspend fun getAllSampleData(): Resource<List<SampleDomainData?>> {
        return domain.getAllSampleData()
    }

}