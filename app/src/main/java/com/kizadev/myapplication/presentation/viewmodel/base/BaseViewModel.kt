package com.kizadev.myapplication.presentation.viewmodel.base

import androidx.annotation.UiThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel

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
    ){
        state.observe(owner, { onChanged(it) })
    }

}