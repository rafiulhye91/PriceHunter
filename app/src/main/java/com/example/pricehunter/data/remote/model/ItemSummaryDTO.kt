package com.example.pricehunter.data.remote.model

import com.example.pricehunter.domain.model.Item

data class ItemSummaryDTO(
    val additionalImageDTOS: List<AdditionalImageDTO>,
    val adultOnly: Boolean,
    val availableCoupons: Boolean,
    val buyingOptions: List<String>,
    val categories: List<CategoryDTO>,
    val condition: String,
    val conditionId: String,
    val currentBidPrice: CurrentBidPriceDTO,
    val epid: String,
    val image: ImageDTO,
    val itemHref: String,
    val itemId: String,
    val itemLocation: ItemLocationDTO,
    val itemWebUrl: String,
    val leafCategoryIds: List<String>,
    val legacyItemId: String,
    val price: PriceDTO,
    val seller: SellerDTO,
    val shippingOptions: List<ShippingOptionDTO>,
    val thumbnailImages: List<ThumbnailImageDTO>,
    val title: String
) {
    fun toItem(): Item {
        return Item(
            itemId = itemId,
            price = price.value,
            currency = price.currency,
            title = title
        )
    }
}