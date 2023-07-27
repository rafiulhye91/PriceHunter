package com.example.pricehunter.data.prefs

import android.content.SharedPreferences
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter
import javax.inject.Inject

class AppPrefs @Inject constructor(private val sharedPrefs: SharedPreferences) {

    companion object {
        private const val KEY_AUTH_CODE = "Auth Code"
        private const val KEY_ACCESS_TOKEN = "Access Token"
        private const val KEY_ACCESS_TOKEN_EXPIRATION = "Access Token Expiration"
    }

    fun setAuthCode(code: String?) {
        sharedPrefs.edit().putString(KEY_AUTH_CODE, code).apply()
    }

    fun getAuthCode(): String? {
        return sharedPrefs.getString(KEY_AUTH_CODE, null)
    }

    fun hasValidAuthCode(): Boolean {
        if (getAuthCode().isNullOrEmpty()) {
            return false
        }
        return true
    }

    fun getAccessToken(): String? {
        return sharedPrefs.getString(KEY_ACCESS_TOKEN, null)
    }

    fun setAccessToken(token: String, expiredAt: String) {
        sharedPrefs.edit().putString(KEY_ACCESS_TOKEN, token).apply()
        sharedPrefs.edit().putString(KEY_ACCESS_TOKEN_EXPIRATION, expiredAt).apply()
    }

    fun getAccessTokenExpiration(): String? {
        return sharedPrefs.getString(KEY_ACCESS_TOKEN_EXPIRATION, null)
    }

    fun hasValidAccessToken(): Boolean {
        if (getAccessToken().isNullOrEmpty()) {
            return false
        }
        return !hasAccessTokenExpired()
    }

    private fun hasAccessTokenExpired(): Boolean {
        if (getAccessTokenExpiration().isNullOrEmpty()) {
            return false
        }
        val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
        val expiredTime = LocalDateTime.parse(getAccessTokenExpiration(), formatter)
        val currentTime = LocalDateTime.now(ZoneId.systemDefault())
        return currentTime.isAfter(expiredTime)
    }
}