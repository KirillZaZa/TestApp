package com.kizadev.myapplication.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kizadev.myapplication.domain.impl.AlbumListUseCaseImpl
import com.kizadev.myapplication.domain.result_wrapper.ResponseResult
import com.kizadev.myapplication.presentation.viewmodel.base.BaseViewModel
import com.kizadev.myapplication.presentation.viewmodel.state.MainScreenState
import com.kizadev.myapplication.presentation.viewmodel.state.ScreenState
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.lang.IllegalArgumentException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainViewModelImpl @Inject constructor(
    private val albumListUseCaseImpl: AlbumListUseCaseImpl,
    private val compositeDisposable: CompositeDisposable
) : BaseViewModel<MainScreenState>(MainScreenState()), MainViewModel {

    private val searchSubject = PublishSubject.create<String>()
    private val searchFlowable = searchSubject.toFlowable(BackpressureStrategy.LATEST)

    override fun handleSearchQuery(searchQuery: String?) {

        if (!searchQuery.isNullOrBlank()) {

            val query = searchQuery.trimEnd()

            if (query != currentState.searchQuery) {
                searchSubject.onNext(query)

                updateState {
                    it.copy(
                        isSearchOpened = true,
                        searchQuery = query,
                        screenState = ScreenState.LOADING
                    )
                }
            }
        } else {

            updateState {

                it.copy(
                    searchQuery = searchQuery,
                    screenState =
                    if (currentState.albumList != null && currentState.albumList!!.isNotEmpty())
                        ScreenState.SHOW_LIST
                    else ScreenState.EMPTY_LIST
                )
            }

            compositeDisposable.clear()
        }

        val disposable = searchFlowable
            .debounce(600, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .filter {
                it.isNotBlank()
            }
            .distinctUntilChanged()
            .switchMap { albumListUseCaseImpl.getAlbums(it) }
            .delay(200, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                when (response) {
                    is ResponseResult.Success -> updateState {
                        it.copy(
                            screenState = ScreenState.SHOW_LIST,
                            albumList = response.result.albumList
                        )
                    }
                    is ResponseResult.Failed -> updateState {
                        it.copy(
                            screenState = ScreenState.FAILED,
                            albumList = null
                        )
                    }
                    else -> {
                        return@subscribe
                    }
                }
            }, {
                updateState { it.copy(screenState = ScreenState.ERROR) }
            })

        compositeDisposable.add(disposable)
    }

    override fun handleSearchState(isOpened: Boolean) {
        updateState {
            it.copy(
                isSearchOpened = isOpened,
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
        searchSubject.onComplete()
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
