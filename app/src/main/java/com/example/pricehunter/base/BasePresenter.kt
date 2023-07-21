package com.example.pricehunter.base

import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope

/**
 *
 * Base class for all presenters. This class provides a common set of methods for handling the lifecycle
 * of a presenter.
 *
 * @author [Rafiul Hye]
 * @since [Current Date or Version]
 */
abstract class BasePresenter(open val view: IBaseView) : IBasePresenter {

    /**
     *
     * Property to get the CoroutineScope of the presenter.
     * This scope is tied to the lifecycle of the view(Activity or fragment).
     */
    val presenterScope: CoroutineScope
        get() = view.lifecycleScope

    override fun start() {
        //no-op
    }

    override fun onResume() {
        //no-op
    }

    override fun onPause() {
        //no-op
    }

    override fun onFinish() {
        //no-op
    }
}