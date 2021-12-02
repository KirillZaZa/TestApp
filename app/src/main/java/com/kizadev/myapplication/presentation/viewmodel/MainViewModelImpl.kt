package com.kizadev.myapplication.presentation.viewmodel

import android.util.Log
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
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import java.lang.IllegalArgumentException
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainViewModelImpl @Inject constructor(
    private val albumListUseCaseImpl: AlbumListUseCaseImpl,
    private val compositeDisposable: CompositeDisposable
) : BaseViewModel<MainScreenState>(MainScreenState()), MainViewModel {

    private val searchSubject = PublishSubject.create<String>()


    override fun handleSearchQuery(searchQuery: String?) {

        updateState { it.copy(
            isSearchOpened = true
        ) }
        Log.e("ViewModel", "$searchQuery", )

        if (!searchQuery.isNullOrBlank()) {

            val query = searchQuery.trimEnd()

            searchSubject
                .onNext(query)

            updateState {
                it.copy(
                    searchQuery = searchQuery,
                    screenState = ScreenState.LOADING
                )
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


        val disposable = searchSubject
            .debounce(300, TimeUnit.MILLISECONDS)
            .filter { query ->
                query.isNotBlank()
            }
            .distinctUntilChanged()
            .subscribe { newQuery ->

                albumListUseCaseImpl.getAlbums(newQuery!!) { response ->

                    when (response) {

                        is ResponseResult.Success -> {
                            Log.e("ViewModelResult", "${response.result.albumList}", )
                            updateState {
                                it.copy(
                                    albumList = response.result.albumList as MutableList<AlbumItem>,
                                    screenState = ScreenState.SHOW_LIST
                                )
                            }
                        }


                        is ResponseResult.Error -> updateState {
                            it.copy(
                                screenState = ScreenState.ERROR,
                                albumList = mutableListOf()
                            )
                        }

                        is ResponseResult.Failed -> updateState {
                            it.copy(
                                screenState = ScreenState.FAILED,
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