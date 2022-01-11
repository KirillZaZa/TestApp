package com.kizadev.myapplication.presentation.fragments

import com.kizadev.myapplication.presentation.viewmodel.state.MainScreenState
import com.kizadev.myapplication.presentation.viewmodel.state.SearchState

interface IMainFragment {

    fun initViews()

    fun setupSearch()

    fun renderSearch(searchState: SearchState)

    fun renderData(screenState: MainScreenState)
}
