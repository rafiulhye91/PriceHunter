package com.example.pricehunter.data.remote.model

data class ConditionDistributionDTO(
    val condition: String,
    val conditionId: String,
    val matchCount: Int,
    val refinementHref: String
)