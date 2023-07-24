package com.example.pricehunter.view.launch

import com.example.pricehunter.base.BasePresenter
import com.example.pricehunter.data.prefs.AppPrefs
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class LaunchPresenter @Inject constructor(
    override val view: ILaunchView,
    private val appPrefs: AppPrefs
) : BasePresenter(view) {

    companion object {
        const val WAITING_TIME: Long = 3000
    }

    override fun start() {
        navigate()
    }

    private fun navigate() {
        presenterScope.launch {
            delay(WAITING_TIME)
            when {
                appPrefs.hasValidAuthCode() -> view.navigateToMainActivity()
                else -> view.navigateToLoginActivity()
            }
        }
    }
}