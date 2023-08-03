package com.example.pricehunter.view.main

import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.core.graphics.scale
import androidx.lifecycle.lifecycleScope
import com.example.pricehunter.R
import com.example.pricehunter.base.BaseActivity
import com.example.pricehunter.databinding.ActivityMainBinding
import com.example.pricehunter.domain.model.ImageRequest
import com.example.pricehunter.util.TAG
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileDescriptor
import java.io.IOException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity(), IMainView {

    companion object {
        private const val IMAGE_HEIGHT = 480
        private const val IMAGE_WIDTH = 640
    }

    private lateinit var binding: ActivityMainBinding

    private lateinit var imageCapture: ImageCapture
    private lateinit var cameraExecutor: ExecutorService

    @Inject
    lateinit var presenter: MainPresenter
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                presenter.onPermissionGranted()
            } else {
                presenter.onPermissionNotGranted()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnHunt.setOnClickListener {
            captureImage()
        }
        cameraExecutor = Executors.newSingleThreadExecutor()
        presenter.start()
    }

    override fun checkForCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                presenter.onPermissionGranted()
            }
            else -> {
                presenter.onPermissionNotGranted()
            }
        }
    }

    override fun askForCameraPermission() {
        requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
    }

    override fun showCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.cameraView.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder()
                .build()

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture
                )
            } catch (e: Exception) {
                showError("${getString(R.string.error_camera)} ${e.message}")
                e.printStackTrace()
            }

        }, ContextCompat.getMainExecutor(this))
    }

    private fun captureImage() {
        showProgress()
        val outputFileOptions = ImageCapture.OutputFileOptions.Builder(createTempImageFile())
            .build()
        imageCapture.takePicture(
            outputFileOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    onImageCapture(outputFileResults.savedUri)
                }

                override fun onError(e: ImageCaptureException) {
                    hideProgress()
                    showError("${getString(R.string.error_capture_image)} ${e.message}")
                    Log.e(TAG(), "Error while capturing image: ${e.message}", e)
                }
            }
        )
    }

    private fun onImageCapture(savedUri: Uri?) {
        lifecycleScope.launch {
            try {
                val imageRequest = savedUri?.let { getBase64Image(uriToBitmap(it)) }
                imageRequest?.let { presenter.searchByImage(it) }
            } catch (e: Exception) {
                showError("${getString(R.string.error_processing_image)} ${e.message}")
                Log.e(TAG(), "Error while processing image: ${e.message}", e)
            } finally {
                hideProgress()
            }
        }
    }

    private fun createTempImageFile(): File {
        return try {
            val folder = File(filesDir, "hunt")
            if (!folder.exists()) {
                folder.mkdir()
            }
            File.createTempFile("temp_image_", ".jpg", folder)
        } catch (e: IOException) {
            showError("${getString(R.string.error_creating_image_file)} ${e.message}")
            Log.e(TAG(), "${getString(R.string.error_creating_image_file)} ${e.message}", e)
            throw e
        }
    }

    private fun getBase64Image(bitmap: Bitmap?): ImageRequest {
        val outputStream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        val byteArray = outputStream.toByteArray()
        val base64ImageString = Base64.encodeToString(byteArray, Base64.NO_WRAP)
        return ImageRequest(base64ImageString)
    }

    private fun uriToBitmap(selectedFileUri: Uri?): Bitmap? {
        if (selectedFileUri == null) {
            return null
        }
        try {
            val parcelFileDescriptor = contentResolver.openFileDescriptor(selectedFileUri, "r")
            val fileDescriptor: FileDescriptor = parcelFileDescriptor!!.fileDescriptor
            val image = BitmapFactory.decodeFileDescriptor(fileDescriptor)
            parcelFileDescriptor.close()
            return image.scale(IMAGE_WIDTH, IMAGE_HEIGHT)
        } catch (e: IOException) {
            showError("${getString(R.string.error_bitmap_conversion)} ${e.message}")
            e.printStackTrace()
        }
        return null
    }

}
