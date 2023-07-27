package com.example.pricehunter.data.remote.model


import com.google.gson.annotations.SerializedName

data class ErrorDTO(
    @SerializedName("error")
    val error: String,
    @SerializedName("error_description")
    val errorDescription: String
)