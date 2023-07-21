package com.example.pricehunter.domain

import io.mockk.every
import io.mockk.mockk
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody

fun generateMockErrorResponseBody(): ResponseBody {
    val mockResponseBody: ResponseBody = mockk()
    every { mockResponseBody.string() } returns "Something bad happened"
    every { mockResponseBody.bytes() } returns byteArrayOf(1, 2, 3)
    every { mockResponseBody.contentType() } returns ("application/json").toMediaTypeOrNull()
    every { mockResponseBody.contentLength() } returns 10L
    return mockResponseBody
}
