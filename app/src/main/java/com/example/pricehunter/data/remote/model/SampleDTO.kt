package com.example.pricehunter.data.remote.model

import com.example.pricehunter.domain.model.SampleDomainData
import com.google.gson.annotations.SerializedName

data class SampleDTO(
    @SerializedName("name")
    val name: String?
) {
    fun toSampleDomainData(): SampleDomainData {
        return SampleDomainData(
            name = this.name
        )
    }
}
