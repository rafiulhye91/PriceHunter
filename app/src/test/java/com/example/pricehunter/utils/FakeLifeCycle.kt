package com.example.pricehunter.utils

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver

class FakeLifeCycle(private val initialState: State = State.STARTED) :
    Lifecycle() {

    private val lifecycleObservers = mutableListOf<LifecycleObserver>()

    override fun addObserver(observer: LifecycleObserver) {
        lifecycleObservers.add(observer)
    }

    override fun removeObserver(observer: LifecycleObserver) {
        lifecycleObservers.remove(observer)
    }

    override fun getCurrentState(): State {
        return initialState
    }

}