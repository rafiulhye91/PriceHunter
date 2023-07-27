package com.example.pricehunter.view.auth

import android.net.Uri
import android.util.Log
import com.example.pricehunter.base.BasePresenter
import com.example.pricehunter.data.Resource
import com.example.pricehunter.data.prefs.AppPrefs
import com.example.pricehunter.service.auth.AuthService
import com.example.pricehunter.view.launch.LaunchPresenter.Companion.accessToken
import com.example.pricehunter.view.launch.LaunchPresenter.Companion.refreshToken
import kotlinx.coroutines.launch
import java.net.URLDecoder
import javax.inject.Inject

class AuthPresenter @Inject constructor(
    override val view: IAuthView,
    private val authService: AuthService,
    private val appPrefs: AppPrefs
) : BasePresenter(view) {

    companion object {
        private const val CLIENT_ID = "RafiulHy-PriceHun-SBX-653724fcb-3ace6e4f"
        private const val REDIRECT_URI = "Rafiul_Hye-RafiulHy-PriceH-jcqtsw"
        private const val SCOPE_LIST =
            "https://api.ebay.com/oauth/api_scope https://api.ebay.com/oauth/api_scope/buy.order.readonly https://api.ebay.com/oauth/api_scope/buy.guest.order https://api.ebay.com/oauth/api_scope/sell.marketing.readonly https://api.ebay.com/oauth/api_scope/sell.marketing https://api.ebay.com/oauth/api_scope/sell.inventory.readonly https://api.ebay.com/oauth/api_scope/sell.inventory https://api.ebay.com/oauth/api_scope/sell.account.readonly https://api.ebay.com/oauth/api_scope/sell.account https://api.ebay.com/oauth/api_scope/sell.fulfillment.readonly https://api.ebay.com/oauth/api_scope/sell.fulfillment https://api.ebay.com/oauth/api_scope/sell.analytics.readonly https://api.ebay.com/oauth/api_scope/sell.marketplace.insights.readonly https://api.ebay.com/oauth/api_scope/commerce.catalog.readonly https://api.ebay.com/oauth/api_scope/buy.shopping.cart https://api.ebay.com/oauth/api_scope/buy.offer.auction https://api.ebay.com/oauth/api_scope/commerce.identity.readonly https://api.ebay.com/oauth/api_scope/commerce.identity.email.readonly https://api.ebay.com/oauth/api_scope/commerce.identity.phone.readonly https://api.ebay.com/oauth/api_scope/commerce.identity.address.readonly https://api.ebay.com/oauth/api_scope/commerce.identity.name.readonly https://api.ebay.com/oauth/api_scope/commerce.identity.status.readonly https://api.ebay.com/oauth/api_scope/sell.finances https://api.ebay.com/oauth/api_scope/sell.item.draft https://api.ebay.com/oauth/api_scope/sell.payment.dispute https://api.ebay.com/oauth/api_scope/sell.item https://api.ebay.com/oauth/api_scope/sell.reputation https://api.ebay.com/oauth/api_scope/sell.reputation.readonly https://api.ebay.com/oauth/api_scope/commerce.notification.subscription https://api.ebay.com/oauth/api_scope/commerce.notification.subscription.readonly"

        private const val loginUrl = "https://auth.sandbox.ebay.com/oauth2/authorize" +
                "?client_id=$CLIENT_ID" +
                "&redirect_uri=$REDIRECT_URI" +
                "&response_type=code" +
                "&scope=$SCOPE_LIST" +
                "&prompt=login"
    }

    fun start(featureCode: String) {
        when (featureCode) {
            accessToken -> {
                getAccessToken()
            }
            refreshToken -> {
                //TODO::get Refresh Token
            }
            else -> {
                view.loadUrl(loginUrl)
            }
        }

    }

    fun onLogInResponse(uri: String) {
        val uri = Uri.parse(uri)
        val code = uri.getQueryParameter("code")
        Log.d("rafi", "code: $code")
        saveToSharedPrefs(code)
        getAccessToken()
    }

    private fun getAccessToken() {
        view.showProgress()
        presenterScope.launch {
            val authCode = URLDecoder.decode(appPrefs.getAuthCode(), "UTF-8")
            if (authCode.isNullOrEmpty()) {
                view.showError("You don't have any auth code")
                start(authCode)
                return@launch
            }
            val result = authService.getAccessToken(authCode, REDIRECT_URI)
            view.hideProgress()
            when (result) {
                is Resource.Success -> {
                    appPrefs.setAccessToken(result.data)
                    Log.d("rafi", "access token: ${result.data?.token}::${result.data?.expiredIn}")
                    Log.d(
                        "rafi",
                        "refresh token: ${result.data?.refreshToken}::${result.data?.refreshTokenExpiredIn}"
                    )
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