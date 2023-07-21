package com.example.pricehunter.view.main

import com.example.pricehunter.base.BasePresenter
import javax.inject.Inject

class MainPresenter @Inject constructor(
    override val view: IMainView
) : BasePresenter(view) {

    override fun start() {
        view.startSampleActivity()
    }
}