package com.example.pricehunter.view.main

import android.util.Log
import com.example.pricehunter.base.BasePresenter
import com.example.pricehunter.data.Resource.Error
import com.example.pricehunter.data.Resource.Success
import com.example.pricehunter.domain.model.Image
import com.example.pricehunter.service.finder.IFinderService
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainPresenter @Inject constructor(
    override val view: IMainView,
    val service: IFinderService
) : BasePresenter(view) {

    override fun start() {

    }

    fun searchByImage(image: Image) {
        view.showProgress()
        presenterScope.launch {
            val result = service.searchByImage(image)
            view.hideProgress()
            when (result) {
                is Success -> result.data?.let {
                    it.forEach { item ->
                        Log.d("rafi", item.title)
                    }
                } ?: view.showError("No data found!")
                is Error -> view.showError(result.error)
            }
        }
    }
}