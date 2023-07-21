package com.example.pricehunter.view.launch

import com.example.pricehunter.base.BasePresenter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class LaunchPresenter @Inject constructor(override val view: ILaunchView) : BasePresenter(view) {

    companion object {
        const val WAITING_TIME: Long = 5000
    }

    override fun start() {
        view.setTitleAnimation()
        navigateToMainActivity()
    }

    private fun navigateToMainActivity() {
        presenterScope.launch {
            delay(WAITING_TIME)
            view.navigateToMainActivity()
        }
    }
}