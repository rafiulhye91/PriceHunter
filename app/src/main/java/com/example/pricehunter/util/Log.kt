package com.example.pricehunter.util

inline fun <reified T> T.TAG(): String = T::class.java.simpleName