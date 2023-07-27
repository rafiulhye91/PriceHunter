package com.example.pricehunter.data.remote.model

data class SearchResultDTO(
    val href: String,
    val itemSummaries: List<ItemSummaryDTO>,
    val limit: Int,
    val next: String,
    val offset: Int,
    val refinement: RefinementDTO,
    val total: Int
)