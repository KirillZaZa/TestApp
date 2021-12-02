package com.kizadev.myapplication.domain.impl

import com.kizadev.myapplication.data.local.model.AlbumListModel
import com.kizadev.myapplication.data.mapper.mapToAlbumListModel
import com.kizadev.myapplication.data.repository.ForaRepositoryImpl
import com.kizadev.myapplication.domain.result_wrapper.ResponseResult
import com.kizadev.myapplication.domain.usecase.AlbumListUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import java.lang.NullPointerException
import javax.inject.Inject

class AlbumListUseCaseImpl @Inject constructor(
    private val repository: ForaRepositoryImpl,
    private val compositeDisposable: CompositeDisposable
) : AlbumListUseCase {


    override fun getAlbums(
        searchQuery: String,
        callback: (ResponseResult<AlbumListModel>) -> Unit
    ) {
        val disposable = repository.getAlbumList(searchQuery)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ albumDto ->

                val albumListModel = albumDto.mapToAlbumListModel()

                if (albumListModel.albumList.isNullOrEmpty()) {

                    callback(ResponseResult.Failed("Таких альбомов не найдено"))

                } else callback(ResponseResult.Success(albumListModel))


            }, { error ->

                callback(ResponseResult.Error(error))

            })

        compositeDisposable.add(disposable)
    }


}