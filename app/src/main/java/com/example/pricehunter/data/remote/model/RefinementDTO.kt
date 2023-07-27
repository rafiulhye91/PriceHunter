package com.example.pricehunter.data.remote.model

data class RefinementDTO(
    val aspectDistributions: List<AspectDistributionDTO>,
    val buyingOptionDistributions: List<BuyingOptionDistributionDTO>,
    val categoryDistributions: List<CategoryDistributionDTO>,
    val conditionDistributions: List<ConditionDistributionDTO>,
    val dominantCategoryId: String
)