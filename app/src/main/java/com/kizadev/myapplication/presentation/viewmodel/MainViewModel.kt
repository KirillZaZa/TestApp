package com.kizadev.myapplication.presentation.viewmodel

interface MainViewModel {

    fun handleSearchQuery(searchQuery: String?)

    fun handleSearchState(isOpened: Boolean)
}
