package com.kizadev.myapplication.presentation.viewmodel.base

import androidx.annotation.UiThread
import androidx.lifecycle.*

abstract class BaseViewModel<T>(
    initialState: T
) : ViewModel() {

    protected val state = MutableLiveData<T>().apply {
        value = initialState
    }

    val currentState
        get() = state.value!!

    @UiThread
    protected inline fun updateState(update: (currentState: T) -> T) {
        val updatedState: T = update(currentState)
        state.value = updatedState
    }

    fun observeState(
        owner: LifecycleOwner,
        onChanged: (newState: T) -> Unit
    ) {
        state.observe(owner, Observer { onChanged(it) })
    }

    fun <D> observeSubState(
        owner: LifecycleOwner,
        transform: (T) -> D,
        onChanged: (newState: D) -> Unit
    ) {
        state
            .map(transform)
            .distinctUntilChanged()
            .observe(owner, Observer { onChanged(it) })
    }
}
