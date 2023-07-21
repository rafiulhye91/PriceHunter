package com.example.pricehunter.view.main

import android.content.Intent
import android.os.Bundle
import com.example.pricehunter.base.BaseActivity
import com.example.pricehunter.databinding.ActivityMainBinding
import com.example.pricehunter.view.sample.SampleActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity(), IMainView {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter.start()
    }

    override fun setWelcomeText(string: String?) {
        binding.tvWelcomeText.text = string
    }

    override fun startSampleActivity() {
        val intent = Intent(this, SampleActivity::class.java)
        startActivity(intent)
    }
}