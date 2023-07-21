package com.example.pricehunter.view.sample

import android.os.Bundle
import android.util.Log
import com.example.pricehunter.base.BaseActivity
import com.example.pricehunter.domain.model.SampleDomainData
import com.example.pricehunter.util.TAG
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SampleActivity : BaseActivity(), ISampleView {

    @Inject
    lateinit var presenter: SamplePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.start()
    }

    override fun setSampleData(data: SampleDomainData?) {
        data?.name?.let { Log.d(TAG(), it) } ?: Log.d(TAG(), "Empty data")
    }

    override fun showError(error: String?) {
        //TODO("Not yet implemented")
    }

    override fun setAllData(data: List<SampleDomainData?>?) {
        //TODO("Not yet implemented")
    }
}