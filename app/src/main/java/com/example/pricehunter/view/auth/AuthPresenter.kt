package com.example.pricehunter.view.auth

import android.net.Uri
import com.example.pricehunter.base.BasePresenter
import com.example.pricehunter.data.Resource
import com.example.pricehunter.data.prefs.AppPrefs
import com.example.pricehunter.service.auth.AuthService
import kotlinx.coroutines.launch
import java.net.URLDecoder
import javax.inject.Inject

class AuthPresenter @Inject constructor(
    override val view: IAuthView,
    private val authService: AuthService,
    private val appPrefs: AppPrefs
) : BasePresenter(view) {

    companion object {
        private const val CLIENT_ID = "RafiulHy-PriceHun-PRD-78cce83ee-f49823da"
        private const val REDIRECT_URI = "Rafiul_Hye-RafiulHy-PriceH-hqjjvd"
        private const val SCOPE_LIST =
            "https://api.ebay.com/oauth/api_scope https://api.ebay.com/oauth/api_scope/sell.marketing.readonly https://api.ebay.com/oauth/api_scope/sell.marketing https://api.ebay.com/oauth/api_scope/sell.inventory.readonly https://api.ebay.com/oauth/api_scope/sell.inventory https://api.ebay.com/oauth/api_scope/sell.account.readonly https://api.ebay.com/oauth/api_scope/sell.account https://api.ebay.com/oauth/api_scope/sell.fulfillment.readonly https://api.ebay.com/oauth/api_scope/sell.fulfillment https://api.ebay.com/oauth/api_scope/sell.analytics.readonly https://api.ebay.com/oauth/api_scope/sell.finances https://api.ebay.com/oauth/api_scope/sell.payment.dispute https://api.ebay.com/oauth/api_scope/commerce.identity.readonly https://api.ebay.com/oauth/api_scope/commerce.notification.subscription https://api.ebay.com/oauth/api_scope/commerce.notification.subscription.readonly"

        private const val loginUrl = "https://auth.ebay.com/oauth2/authorize" +
                "?client_id=$CLIENT_ID" +
                "&redirect_uri=$REDIRECT_URI" +
                "&response_type=code" +
                "&scope=$SCOPE_LIST"
    }

    override fun start() {
        when {
            !appPrefs.hasValidAuthCode() -> {
                view.loadUrl(loginUrl)
                return
            }
            !appPrefs.hasValidAccessToken() && !appPrefs.hasValidRefreshToken() -> {
                getAccessToken()
                return
            }
            !appPrefs.hasValidAccessToken() && appPrefs.hasValidRefreshToken() -> {
                getRefreshAccessToken()
                return
            }
            else -> {
                view.navigateToMainActivity()
            }
        }

    }

    private fun getRefreshAccessToken() {
        view.showProgress()
        presenterScope.launch {
            val refreshToken = appPrefs.getRefreshToken()
            if (refreshToken.isNullOrEmpty()) {
                view.showError("You don't have access")
                return@launch
            }
            val result = authService.getRefreshAccessToken(refreshToken, SCOPE_LIST)
            view.hideProgress()
            when (result) {
                is Resource.Success -> {
                    appPrefs.setRefreshAccessToken(result.data)
                    view.navigateToMainActivity()
                }
                is Resource.Error -> {
                    view.showError(result.error)
                }
            }
        }

    }

    fun onLogInResponse(uri: String) {
        val uri = Uri.parse(uri)
        val code = uri.getQueryParameter("code")
        saveToSharedPrefs(code)
        getAccessToken()
    }

    private fun getAccessToken() {
        view.showProgress()
        presenterScope.launch {
            val authCode = URLDecoder.decode(appPrefs.getAuthCode(), "UTF-8")
            if (authCode.isNullOrEmpty()) {
                view.showError("You don't have any auth code")
                start()
                return@launch
            }
            val result = authService.getAccessToken(authCode, REDIRECT_URI)
            view.hideProgress()
            when (result) {
                is Resource.Success -> {
                    appPrefs.setAccessToken(result.data)
                    view.navigateToMainActivity()
                }
                is Resource.Error -> {
                    view.showError(result.error)
                }

            }

        }
    }

    private fun saveToSharedPrefs(code: String?) {
        appPrefs.setAuthCode(code)
    }
}