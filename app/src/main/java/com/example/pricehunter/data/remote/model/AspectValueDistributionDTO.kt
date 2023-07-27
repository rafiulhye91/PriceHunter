package com.example.pricehunter.data.remote.model

data class AspectValueDistributionDTO(
    val localizedAspectValue: String,
    val matchCount: Int,
    val refinementHref: String
)