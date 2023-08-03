package com.example.pricehunter.view.main

import android.util.Log
import com.example.pricehunter.base.BasePresenter
import com.example.pricehunter.data.Resource.Error
import com.example.pricehunter.data.Resource.Success
import com.example.pricehunter.data.prefs.AppPrefs
import com.example.pricehunter.domain.model.ImageRequest
import com.example.pricehunter.service.finder.IFinderService
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainPresenter @Inject constructor(
    override val view: IMainView,
    private val appPrefs: AppPrefs,
    private val service: IFinderService
) : BasePresenter(view) {

    override fun start() {
        view.checkForCameraPermission()
    }

    fun searchByImage(imageRequest: ImageRequest) {
        val accessToken = appPrefs.getAccessToken()
        if (accessToken.isNullOrEmpty()) {
            view.showError("No access token!")
            return
        }
        view.showProgress()
        presenterScope.launch {
            val result = service.searchByImage("Bearer $accessToken", imageRequest)
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

    fun onPermissionGranted() {
        view.showCamera()
    }

    fun onPermissionNotGranted() {
        view.askForCameraPermission()
    }
}