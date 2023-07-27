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
                view.navToAuthActivity()
            }
            !appPrefs.hasValidAccessToken() -> {
                view.navToAuthActivity()
            }
            else -> {
                view.navigateToMainActivity()
            }
        }
    }
}