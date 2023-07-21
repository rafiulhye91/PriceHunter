package com.example.pricehunter.data.remote.model


import com.google.gson.annotations.SerializedName

data class SampleErrorDTO(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String
)