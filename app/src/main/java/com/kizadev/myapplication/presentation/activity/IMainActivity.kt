package com.kizadev.myapplication.presentation.activity

import com.kizadev.myapplication.presentation.viewmodel.state.MainScreenState

interface IMainActivity {


    fun initViews()

    fun renderData(screenState: MainScreenState)

}