package com.example.pricehunter.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.pricehunter.R
import com.example.pricehunter.databinding.DialogLoadingBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 *
 * BaseActivity class that implements [IBaseView] interface, it provides a common set of methods for handling UI states.
 * This class can be extended by other activities to provide a consistent UI experience.
 *
 * @author [Rafiul Hye]
 * @since [Current Date or Version]
 */

abstract class BaseActivity : AppCompatActivity(), IBaseView {
    private val progressDialogBinding by lazy {
        DialogLoadingBinding.inflate(layoutInflater)
    }
    private val progressDialog: AlertDialog by lazy {
        MaterialAlertDialogBuilder(this).create()
    }

    override fun showProgress() {
        showProgress(getString(R.string.loading))
    }

    override fun showProgress(message: String?) {
        progressDialogBinding.tvProgressMsg.text = message
        progressDialog.setView(progressDialogBinding.root)
        progressDialog.setCancelable(false)
        progressDialog.show()
    }

    override fun hideProgress() {
        if (progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }

    override fun showError(error: String?) {
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.error))
            .setMessage(error)
            .setPositiveButton(getString(R.string.ok)) { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    override fun navigateToActivity(
        context: Context,
        destinationActivity: Class<*>,
        shouldFinish: Boolean
    ) {
        val intent = Intent(context, destinationActivity)
        context.startActivity(intent)
        if (shouldFinish && context is Activity) {
            context.finish()
        }
    }
}