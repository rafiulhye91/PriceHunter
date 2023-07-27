package com.example.pricehunter.view.auth

import com.example.pricehunter.base.IBaseView

interface IAuthView : IBaseView {
    fun loadUrl(loginUrl: String)
    fun finishActivity()
    fun navigateToMainActivity()
}