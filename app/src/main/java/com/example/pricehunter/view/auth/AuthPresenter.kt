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
        private const val CLIENT_ID = "RafiulHy-PriceHun-SBX-653724fcb-3ace6e4f"
        private const val REDIRECT_URI = "Rafiul_Hye-RafiulHy-PriceH-jcqtsw"
        private const val SCOPE_LIST =
            "https://api.ebay.com/oauth/api_scope/commerce.catalog.readonly"

        private const val loginUrl = "https://auth.sandbox.ebay.com/oauth2/authorize" +
                "?client_id=$CLIENT_ID" +
                "&redirect_uri=$REDIRECT_URI" +
                "&response_type=code" +
                "&scope=$SCOPE_LIST" +
                "&prompt=login"
    }

    override fun start() {
        when {
            !appPrefs.hasValidAuthCode() -> {
                view.loadUrl(loginUrl)
            }
            !appPrefs.hasValidAccessToken() && !appPrefs.hasValidRefreshToken() -> {
                getAccessToken()
            }
            !appPrefs.hasValidAccessToken() && appPrefs.hasValidRefreshToken() -> {
                getRefreshAccessToken()
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
                start()
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