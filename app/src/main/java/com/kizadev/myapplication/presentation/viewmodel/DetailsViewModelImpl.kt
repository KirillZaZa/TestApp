package com.kizadev.myapplication.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kizadev.myapplication.domain.model.AlbumItem
import com.kizadev.myapplication.domain.model.TrackItem
import com.kizadev.myapplication.domain.result_wrapper.ResponseResult
import com.kizadev.myapplication.domain.usecase.GetAlbumDetailsFromNetworkUseCase
import com.kizadev.myapplication.presentation.viewmodel.base.BaseViewModel
import com.kizadev.myapplication.presentation.viewmodel.state.DetailScreenState
import com.kizadev.myapplication.presentation.viewmodel.state.ScreenState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.IllegalArgumentException

class DetailsViewModelImpl(
    private val albumItem: AlbumItem,
    private val getAlbumDetailsFromNetworkUseCase: GetAlbumDetailsFromNetworkUseCase,
) : BaseViewModel<DetailScreenState>(DetailScreenState()), DetailsViewModel {

    override fun getAlbumDetails() {

        updateState {
            it.copy(
                albumItem = albumItem,
                screenState = ScreenState.LOADING
            )
        }

        val disposable = getAlbumDetailsFromNetworkUseCase.execute(albumItem.albumId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                when (response) {
                    is ResponseResult.Success -> {

                        updateState {
                            it.copy(
                                trackList = response.result.albumTracksList as MutableList<TrackItem>,
                                screenState = ScreenState.SHOW_LIST
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
                    else -> {
                        return@subscribe
                    }
                }
            }, {

                updateState {
                    it.copy(
                        screenState = ScreenState.ERROR
                    )
                }
            })
    }
}

class DetailsViewModelFactory @AssistedInject constructor(
    @Assisted("albumItem") private val albumItem: AlbumItem,
    private val getAlbumDetailsFromNetworkUseCase: GetAlbumDetailsFromNetworkUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsViewModelImpl::class.java)) {
            return DetailsViewModelImpl(
                albumItem, getAlbumDetailsFromNetworkUseCase
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
