package com.example.pricehunter.view.launch

import android.os.Bundle
import android.view.View.VISIBLE
import com.example.pricehunter.base.BaseActivity
import com.example.pricehunter.databinding.ActivityLaunchBinding
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

    override fun setTitleAnimation() {
        binding.tvTitle.apply {
            scaleX = 1f
            scaleY = 1f
            visibility = VISIBLE
            animate()
                .scaleX(3f)
                .scaleY(3f)
                .setDuration(3000)
                .start()
        }
    }

    override fun navigateToMainActivity() {
        navigateToActivity(
            context = this,
            destinationActivity = MainActivity::class.java,
            shouldFinish = true
        )
    }
}