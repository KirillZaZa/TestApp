package com.kizadev.myapplication.domain.result_wrapper

sealed class ResponseResult<T> {

    data class Success<T>(val result: T) : ResponseResult<T>()

    data class Error<T>(val throwable: Throwable) : ResponseResult<T>()

    data class Failed<T>(val msg: String) : ResponseResult<T>()
}
