package com.kizadev.myapplication.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kizadev.myapplication.data.local.model.AlbumItem
import com.kizadev.myapplication.domain.impl.AlbumListUseCaseImpl
import com.kizadev.myapplication.domain.result_wrapper.ResponseResult
import com.kizadev.myapplication.presentation.viewmodel.base.BaseViewModel
import com.kizadev.myapplication.presentation.viewmodel.state.MainScreenState
import com.kizadev.myapplication.presentation.viewmodel.state.ScreenState
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableOnSubscribe
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.processors.PublishProcessor
import java.lang.IllegalArgumentException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainViewModelImpl @Inject constructor(
    private val albumListUseCaseImpl: AlbumListUseCaseImpl,
    private val compositeDisposable: CompositeDisposable
) : BaseViewModel<MainScreenState>(MainScreenState()), MainViewModel {

    private val searchFlowable = PublishProcessor.create<String>()


    override fun handleSearchQuery(searchQuery: String) {

        updateState {
            it.copy(
                searchQuery = searchQuery,
                screenState = ScreenState.LOADING
            )
        }

        searchFlowable.onNext(searchQuery)

        val disposable = searchFlowable
            .debounce(300, TimeUnit.MILLISECONDS)
            .filter { query ->
                query.isBlank()
            }
            .distinctUntilChanged()
            .switchMap { latestQuery ->
                Flowable.create(FlowableOnSubscribe<String> {
                    it.onNext(latestQuery)
                }, BackpressureStrategy.LATEST)
            }.subscribe { newQuery ->

                albumListUseCaseImpl.getAlbums(newQuery) { response ->
                    when (response) {
                        is ResponseResult.Success ->
                            updateState {
                                it.copy(
                                    albumList = response.result.albumList as MutableList<AlbumItem>,
                                    screenState = ScreenState.SHOW_LIST
                                )
                            }


                        else -> updateState {
                            it.copy(
                                screenState = ScreenState.ERROR,
                                albumList = mutableListOf()
                            )
                        }

                    }
                }

            }

        compositeDisposable.add(disposable)
    }

    override fun handleSearchState(isOpened: Boolean) {
        updateState {
            it.copy(
                isSearchOpened = isOpened,
            )
        }
    }


}

class MainViewModelFactory @AssistedInject constructor(
    private val albumListUseCaseImpl: AlbumListUseCaseImpl,
    private val compositeDisposable: CompositeDisposable
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModelImpl::class.java)) {
            return MainViewModelImpl(
                albumListUseCaseImpl,
                compositeDisposable
            ) as T
        }
        throw IllegalArgumentException()
    }

    @AssistedFactory
    interface Factory {
        fun create(): MainViewModelFactory
    }

}