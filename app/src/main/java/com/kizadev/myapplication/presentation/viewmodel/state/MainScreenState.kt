package com.kizadev.myapplication.presentation.viewmodel.state

import com.kizadev.myapplication.data.local.model.AlbumItem


data class MainScreenState(
    val screenState: ScreenState = ScreenState.EMPTY_LIST,
    val albumList: MutableList<AlbumItem>? = null,
    val searchQuery: String? = null,
    val isSearchOpened: Boolean = false
)
