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
        private const val WAITING_TIME: Long = 3000
        const val authCode = "Auth Code"
        const val accessToken = "Access Token"
    }

    override fun start() {
        presenterScope.launch {
            delay(WAITING_TIME)
            navigate()
        }
    }

    private fun navigate() {
        if (!appPrefs.hasValidAuthCode()) {
            view.navToAuthActivity(authCode)
            return
        }
        if (!appPrefs.hasValidAccessToken()) {
            view.navToAuthActivity(accessToken)
            return
        }
        view.navigateToMainActivity()
    }
}