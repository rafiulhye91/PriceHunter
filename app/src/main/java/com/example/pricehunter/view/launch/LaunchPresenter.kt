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
        const val refreshToken = "Refresh Token"
    }

    override fun start() {
        presenterScope.launch {
            delay(WAITING_TIME)
            navigate()
        }
    }

    private fun navigate() {
        when {
            !appPrefs.hasValidAuthCode() -> {
                view.navToAuthActivity(authCode)
            }
            !appPrefs.hasValidAccessToken() && !appPrefs.hasValidRefreshToken() -> {
                view.navToAuthActivity(accessToken)
            }
            !appPrefs.hasValidAccessToken() && appPrefs.hasValidRefreshToken() -> {
                view.navToAuthActivity(refreshToken)
            }
            else -> {
                view.navigateToMainActivity()
            }
        }
    }
}