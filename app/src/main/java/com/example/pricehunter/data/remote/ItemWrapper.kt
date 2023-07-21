package com.example.pricehunter.data

import retrofit2.Response

data class ItemWrapper<T, V>(
    val data: T?,
    val response: Response<V?>
)

data class ListItemWrapper<T, V>(
    val data: List<T?>?,
    val response: Response<V?>
)
