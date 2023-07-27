package com.example.pricehunter.data.prefs

import android.content.SharedPreferences
import com.example.pricehunter.domain.model.AccessToken
import com.example.pricehunter.util.TimeUtils
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter
import javax.inject.Inject

class AppPrefs @Inject constructor(private val sharedPrefs: SharedPreferences) {

    companion object {
        private const val KEY_AUTH_CODE = "Auth Code"
        private const val KEY_ACCESS_TOKEN = "Access Token"
        private const val KEY_REFRESH_TOKEN = "Refresh Token"
        private const val KEY_ACCESS_TOKEN_EXPIRATION = "Access Token Expiration"
        private const val KEY_REFRESH_TOKEN_EXPIRATION = "Refresh Token Expiration"
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

    fun setAccessToken(accessToken: AccessToken?) {
        if (accessToken == null) {
            return
        }
        sharedPrefs.edit().putString(KEY_ACCESS_TOKEN, accessToken.token).apply()
        sharedPrefs.edit().putString(KEY_REFRESH_TOKEN, accessToken.refreshToken).apply()
        val expiredAt = TimeUtils.getExpirationTime(accessToken.expiredIn)
        sharedPrefs.edit().putString(KEY_ACCESS_TOKEN_EXPIRATION, expiredAt).apply()
        val refreshTokenExpiredAt = TimeUtils.getExpirationTime(accessToken.refreshTokenExpiredIn)
        sharedPrefs.edit().putString(KEY_REFRESH_TOKEN_EXPIRATION, refreshTokenExpiredAt).apply()
    }

    private fun getAccessTokenExpiration(): String? {
        return sharedPrefs.getString(KEY_ACCESS_TOKEN_EXPIRATION, null)
    }

    fun hasValidAccessToken(): Boolean {
        if (getAccessToken().isNullOrEmpty()) {
            return false
        }
        return !hasAccessTokenExpired()
    }

    fun hasValidRefreshToken(): Boolean {
        if (getRefreshToken().isNullOrEmpty()) {
            return false
        }
        return !hasRefreshTokenExpired()
    }

    private fun getRefreshToken(): String? {
        return sharedPrefs.getString(KEY_REFRESH_TOKEN, null)
    }

    private fun hasAccessTokenExpired(): Boolean {
        val accessTokenExpiration = getAccessTokenExpiration()
        if (accessTokenExpiration.isNullOrEmpty()) {
            return false
        }
        val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
        val expiredTime = LocalDateTime.parse(accessTokenExpiration, formatter)
        val currentTime = LocalDateTime.now(ZoneId.systemDefault())
        return currentTime.isAfter(expiredTime)
    }

    private fun hasRefreshTokenExpired(): Boolean {
        val refreshTokenExpiration = getRefreshTokenExpiration()
        if (refreshTokenExpiration.isNullOrEmpty()) {
            return false
        }
        val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
        val expiredTime = LocalDateTime.parse(refreshTokenExpiration, formatter)
        val currentTime = LocalDateTime.now(ZoneId.systemDefault())
        return currentTime.isAfter(expiredTime)
    }

    private fun getRefreshTokenExpiration(): String? {
        return sharedPrefs.getString(KEY_REFRESH_TOKEN_EXPIRATION, null)
    }
}