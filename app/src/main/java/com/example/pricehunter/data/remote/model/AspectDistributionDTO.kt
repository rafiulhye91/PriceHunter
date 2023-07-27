package com.example.pricehunter.data.remote.model

data class AspectDistributionDTO(
    val aspectValueDistributions: List<AspectValueDistributionDTO>,
    val localizedAspectName: String
)