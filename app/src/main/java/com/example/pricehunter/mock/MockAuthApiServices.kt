package com.example.pricehunter.mock

import android.app.Application
import android.util.Log
import com.example.pricehunter.data.remote.AuthApiServices
import com.example.pricehunter.data.remote.model.AccessTokenDTO
import com.example.pricehunter.data.remote.model.RefreshTokenDTO
import com.example.pricehunter.data.remote.model.SampleDTO
import com.example.pricehunter.util.TAG
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.IOException
import okio.buffer
import okio.source
import retrofit2.Response
import java.io.FileNotFoundException
import javax.inject.Inject

class MockAuthApiServices @Inject constructor(private val app: Application) :
    AuthApiServices {
    override suspend fun getAccessToken(
        grantType: String,
        code: String,
        redirectUri: String
    ): Response<AccessTokenDTO?> {
        return app.generateMockResponse("mock/access_token.json")
    }

    override suspend fun getRefreshAccessToken(
        grantType: String,
        refreshToken: String,
        scope: String
    ): Response<RefreshTokenDTO?> {
        return app.generateMockResponse("mock/refresh_token.json")
    }

    override suspend fun getSampleData(): Response<SampleDTO?> {
        return app.generateMockResponse("mock/sample_mock_response.json")
    }
}

private const val MOCK_RESPONSE_DELAY: Long = 2000L

private suspend inline fun <reified T> Application.generateMockResponse(
    fileName: String,
    errorFileName: String? = null
): Response<T> {
    val jsonString = getJsonString(fileName)
        ?: throw IllegalArgumentException("File not found: $fileName")
    withContext(Dispatchers.IO) { delay(MOCK_RESPONSE_DELAY) }
    if (!errorFileName.isNullOrEmpty()) {
        return generateErrorResponse(errorFileName)
    }
    return Response.success(
        Gson().fromJson(jsonString, T::class.java)
    )
}

private fun <T> Application.generateErrorResponse(errorFileName: String): Response<T> {
    val jsonString = getJsonString(errorFileName) ?: ""
    val responseBody = jsonString.toResponseBody("application/json".toMediaTypeOrNull())
    val rawResponse = okhttp3.Response.Builder()
        .code(400) // Set the desired HTTP error code here
        .message("Mock Error Response")
        .protocol(Protocol.HTTP_1_1)
        .request(Request.Builder().url("http://mock.error").build())
        .build()
    return Response.error(responseBody, rawResponse)
}

private fun Application.getJsonString(file: String): String? {
    var json: String? = null
    try {
        val source = assets.open(file).source().buffer()
        json = source.readUtf8()
    } catch (ioe: IOException) {
        Log.e(TAG(), "Unable to load json file.")
    } catch (fnfe: FileNotFoundException) {
        Log.e(TAG(), "Specified json file not found: $file")
    }
    return json
}
