package com.example.pricehunter.base

/**
 *
 * Interface for the base presenter. This interface is implemented by all presenters in the app
 * to provide a common set of methods for handling presenter lifecycle.
 *
 * @author [Rafiul Hye]
 * @since [Current Date or Version]
 */
interface IBasePresenter {
    /**
     *
     * Method to be called when the presenter is first initialized.
     */
    fun start()

    /**
     *
     * Method to be called when the presenter's corresponding view is resumed.
     */
    fun onResume()

    /**
     *
     * Method to be called when the presenter's corresponding view is paused.
     */
    fun onPause()

    /**
     *
     * Method to be called when the presenter is no longer needed and should be cleaned up.
     */
    fun onFinish()
}