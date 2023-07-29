package com.example.pricehunter.view.main

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Base64
import com.example.pricehunter.base.BaseActivity
import com.example.pricehunter.databinding.ActivityMainBinding
import com.example.pricehunter.domain.model.Image
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream
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

    private fun getBase64Image(bitmap: Bitmap): Image {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        val byteArray = outputStream.toByteArray()
        val base64ImageString = Base64.encodeToString(byteArray, Base64.NO_WRAP)
        return Image(base64ImageString)
    }

    override fun checkForCameraPermission() {
//        TODO("Not yet implemented")
    }

    override fun askForCameraPermission() {
//        TODO("Not yet implemented")
    }

    override fun showCamera() {
//        TODO("Not yet implemented")
    }

}
