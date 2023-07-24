package com.example.pricehunter.data.remote.model

data class ShippingOptionDTO(
    val shippingCost: ShippingCostDTO,
    val shippingCostType: String
)