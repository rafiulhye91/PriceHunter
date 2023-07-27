package com.example.pricehunter.view.auth

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.pricehunter.base.BaseActivity
import com.example.pricehunter.databinding.ActivityAuthBinding
import com.example.pricehunter.view.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AuthActivity : BaseActivity(), IAuthView {
    @Inject
    lateinit var presenter: AuthPresenter

    private lateinit var binding: ActivityAuthBinding

    companion object {
        fun newInstance(context: Context): Intent {
            return Intent(context, AuthActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setWebView()
        presenter.start()
    }

    private fun setWebView() {
        binding.wvAuth.settings.javaScriptEnabled = true
        binding.wvAuth.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                val url = request?.url?.toString()
                if (url != null && url.contains("isAuthSuccessful=true")) {
                    presenter.onLogInResponse(url)
                    return true
                }
                return super.shouldOverrideUrlLoading(view, request)
            }
        }
    }

    override fun loadUrl(loginUrl: String) {
        binding.wvAuth.loadUrl(loginUrl)
    }

    override fun finishActivity() {
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun navigateToMainActivity() {
        navigateToActivity(this, MainActivity::class.java, true)
    }

}