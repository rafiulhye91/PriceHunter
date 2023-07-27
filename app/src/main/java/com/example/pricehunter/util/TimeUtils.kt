package com.example.pricehunter.util

import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter

object TimeUtils {
    fun getExpirationTime(expiredIn: Long): String {
        val currentTime = LocalDateTime.now(ZoneId.systemDefault())
        val expiredAt = currentTime.plusSeconds(expiredIn).toString()
        val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
        return expiredAt.format(formatter)
    }
}