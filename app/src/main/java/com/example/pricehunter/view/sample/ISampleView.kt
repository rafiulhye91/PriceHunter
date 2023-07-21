package com.example.pricehunter.view.sample

import com.example.pricehunter.base.IBaseView
import com.example.pricehunter.domain.model.SampleDomainData

interface ISampleView : IBaseView {
    fun setSampleData(data: SampleDomainData?)
    fun setAllData(data: List<SampleDomainData?>?)
}