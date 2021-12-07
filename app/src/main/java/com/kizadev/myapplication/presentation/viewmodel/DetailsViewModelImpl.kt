package com.kizadev.myapplication.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kizadev.myapplication.data.local.model.AlbumItem
import com.kizadev.myapplication.data.local.model.TrackItem
import com.kizadev.myapplication.domain.impl.AlbumDetailsUseCaseImpl
import com.kizadev.myapplication.domain.result_wrapper.ResponseResult
import com.kizadev.myapplication.presentation.viewmodel.base.BaseViewModel
import com.kizadev.myapplication.presentation.viewmodel.state.DetailScreenState
import com.kizadev.myapplication.presentation.viewmodel.state.ScreenState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import java.lang.IllegalArgumentException

class DetailsViewModelImpl(
    private val albumItem: AlbumItem,
    private val albumDetailsUseCaseImpl: AlbumDetailsUseCaseImpl
) : BaseViewModel<DetailScreenState>(DetailScreenState()), DetailsViewModel {

    override fun getAlbumDetails() {

        updateState {
            it.copy(
                albumItem = albumItem,
                screenState = ScreenState.LOADING
            )
        }

        albumDetailsUseCaseImpl.getAlbumDetails(albumItem.albumId) { response ->
            when (response) {
                is ResponseResult.Success -> {

                    updateState {
                        it.copy(
                            trackList = response.result.albumTracksList as MutableList<TrackItem>,
                            screenState = ScreenState.SHOW_LIST
                        )
                    }
                }

                is ResponseResult.Error -> {

                    updateState {
                        it.copy(
                            screenState = ScreenState.ERROR
                        )
                    }
                }

                is ResponseResult.Failed -> {

                    updateState {
                        it.copy(
                            screenState = ScreenState.FAILED
                        )
                    }
                }
            }
        }
    }
}

class DetailsViewModelFactory @AssistedInject constructor(
    @Assisted("albumItem") private val albumItem: AlbumItem,
    private val albumDetailsUseCaseImpl: AlbumDetailsUseCaseImpl
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsViewModelImpl::class.java)) {
            return DetailsViewModelImpl(
                albumItem, albumDetailsUseCaseImpl
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("albumItem") albumItem: AlbumItem
        ): DetailsViewModelFactory
    }
}
