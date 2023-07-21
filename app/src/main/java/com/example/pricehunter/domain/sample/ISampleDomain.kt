package com.example.pricehunter.domain.sample

import com.example.pricehunter.data.Resource
import com.example.pricehunter.domain.model.SampleDomainData

interface ISampleDomain {
    suspend fun getSampleData(): Resource<SampleDomainData?>
    suspend fun getAllSampleData(): Resource<List<SampleDomainData?>>
}