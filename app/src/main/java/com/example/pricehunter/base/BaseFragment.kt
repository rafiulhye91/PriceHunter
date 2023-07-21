package com.example.pricehunter.base

import androidx.fragment.app.Fragment

/**
 *
 * Abstract class for the base fragment. This class is a child of [Fragment] and implements [IBaseView] interface
 * to provide a common set of methods for handling ui states. It also delegates the ui handling methods to the
 * parent [BaseActivity] class.
 *
 * @author [Rafiul Hye]
 * @since [Current Date or Version]
 */

abstract class BaseFragment : Fragment(), IBaseView {
    private val baseActivity: BaseActivity?
        get() = activity as? BaseActivity

    override fun showProgress() {
        baseActivity?.showProgress()
    }

    override fun showProgress(message: String?) {
        baseActivity?.showProgress(message)
    }

    override fun hideProgress() {
        baseActivity?.hideProgress()
    }

    override fun showError(error: String?) {
        baseActivity?.showError(error)
    }
}