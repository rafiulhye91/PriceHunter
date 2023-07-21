package com.example.pricehunter.service.sample

import com.example.pricehunter.data.Resource
import com.example.pricehunter.domain.model.SampleDomainData

interface ISampleService {
    suspend fun getSampleData(): Resource<SampleDomainData?>
    suspend fun getAllSampleData(): Resource<List<SampleDomainData?>>
}