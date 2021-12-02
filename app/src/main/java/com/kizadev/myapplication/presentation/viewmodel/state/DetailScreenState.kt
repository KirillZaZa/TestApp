package com.kizadev.myapplication.presentation.viewmodel.state

import com.kizadev.myapplication.data.local.model.AlbumItem
import com.kizadev.myapplication.data.local.model.TrackItem

data class DetailScreenState(
    val trackList: MutableList<TrackItem>? = null,
    val albumItem: AlbumItem? = null,
    val screenState: ScreenState = ScreenState.LOADING
)
