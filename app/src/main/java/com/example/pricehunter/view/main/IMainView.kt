package com.example.pricehunter.view.main

import com.example.pricehunter.base.IBaseView

interface IMainView : IBaseView {
    fun setWelcomeText(string: String?)
    fun startSampleActivity()
}