package com.example.pricehunter.base

import android.content.Context
import androidx.lifecycle.LifecycleOwner

/**
 *
 * Interface for the base view. This interface is implemented by the base [Activity] and [Fragment] classes
 * to provide a common set of methods for handling ui states.
 *
 * @author [Rafiul Hye]
 * @since [Current Date or Version]
 */
interface IBaseView : LifecycleOwner {
    /**
     *
     * Show the progress dialog with a default message.
     */
    fun showProgress()

    /**
     *
     * Show the progress dialog with the provided message.
     * @param message The message to be displayed on the progress dialog.
     */
    fun showProgress(message: String?)

    /**
     *
     * Hide the progress dialog.
     */
    fun hideProgress()

    /**
     *
     * Show an error dialog with the provided error message
     * @param error The error message to be displayed on the error dialog.
     */
    fun showError(error: String?)

    /**
     * Navigate to the specified activity.
     *
     * @param context The context from which the navigation is initiated.
     * @param destinationActivity The target activity class to navigate to.
     * @param shouldFinish Whether the current activity should be finished after navigation.
     */
    fun navigateToActivity(
        context: Context,
        destinationActivity: Class<*>,
        shouldFinish: Boolean
    )
}