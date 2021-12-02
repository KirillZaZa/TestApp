package com.kizadev.myapplication.presentation.fragments

import com.kizadev.myapplication.presentation.viewmodel.state.DetailScreenState

interface IAlbumDetailsFragment {

    fun initViews()

    fun renderData(state: DetailScreenState)

}