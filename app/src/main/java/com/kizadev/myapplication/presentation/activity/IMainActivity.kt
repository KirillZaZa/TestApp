package com.kizadev.myapplication.presentation.activity

import com.kizadev.myapplication.presentation.viewmodel.state.MainScreenState

interface IMainFragment{


    fun initViews()

    fun renderData(screenState: MainScreenState)

}