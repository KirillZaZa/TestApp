package com.kizadev.myapplication.presentation.viewmodel.state

data class SearchState(
    val searchQuery: String? = null,
    val isOpened: Boolean = false
)