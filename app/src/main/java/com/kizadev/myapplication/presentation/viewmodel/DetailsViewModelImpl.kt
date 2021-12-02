package com.kizadev.myapplication.presentation.viewmodel

import com.kizadev.myapplication.data.local.model.AlbumItem
import com.kizadev.myapplication.domain.impl.AlbumDetailsUseCaseImpl
import com.kizadev.myapplication.presentation.viewmodel.base.BaseViewModel
import com.kizadev.myapplication.presentation.viewmodel.state.DetailScreenState

class DetailsViewModelImpl(
    private val albumDetailsUseCaseImpl: AlbumDetailsUseCaseImpl,
    private val albumItem: AlbumItem
) : BaseViewModel<DetailScreenState>(DetailScreenState()) {


    init {
        updateState { it.copy(
            albumItem = albumItem
        ) }
    }


}