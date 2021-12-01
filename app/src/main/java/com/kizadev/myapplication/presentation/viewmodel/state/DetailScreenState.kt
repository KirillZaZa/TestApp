package com.kizadev.myapplication.presentation.viewmodel.state

import com.kizadev.myapplication.data.local.model.AlbumDetailsModel

data class DetailScreenState(
    val albumDetails: AlbumDetailsModel,
    val screenState: ScreenState = ScreenState.EMPTY_LIST
)
