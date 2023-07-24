package com.example.pricehunter.view.launch

import android.os.Bundle
import com.example.pricehunter.base.BaseActivity
import com.example.pricehunter.databinding.ActivityLaunchBinding
import com.example.pricehunter.view.login.LoginActivity
import com.example.pricehunter.view.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LaunchActivity : BaseActivity(), ILaunchView {
    @Inject
    lateinit var presenter: LaunchPresenter

    private lateinit var binding: ActivityLaunchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLaunchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter.start()
    }

    override fun navigateToMainActivity() {
        navigateToActivity(
            context = this,
            destinationActivity = MainActivity::class.java,
            shouldFinish = true
        )
    }

    override fun navigateToLoginActivity() {
        navigateToActivity(
            context = this,
            destinationActivity = LoginActivity::class.java,
            shouldFinish = true
        )
    }
}