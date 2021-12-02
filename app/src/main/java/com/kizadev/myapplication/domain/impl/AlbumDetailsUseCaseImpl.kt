package com.kizadev.myapplication.domain.impl

import com.kizadev.myapplication.data.local.model.AlbumDetailsModel
import com.kizadev.myapplication.data.mapper.mapToAlbumDetailsModel
import com.kizadev.myapplication.data.repository.ForaRepositoryImpl
import com.kizadev.myapplication.domain.result_wrapper.ResponseResult
import com.kizadev.myapplication.domain.usecase.AlbumDetailsUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class AlbumDetailsUseCaseImpl @Inject constructor(
    private val repository: ForaRepositoryImpl,
    private val compositeDisposable: CompositeDisposable
) : AlbumDetailsUseCase {


    override fun getAlbumDetails(
        collectionId: String,
        callback: (ResponseResult<AlbumDetailsModel>) -> Unit
    ) {
        val disposable = repository.getAlbumDetails(collectionId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ albumDetailsDto ->
                val albumDetails = albumDetailsDto.mapToAlbumDetailsModel()

                if (albumDetails.albumTracksList.isNullOrEmpty()) {
                    callback(ResponseResult.Failed("Failed"))
                }

                callback(ResponseResult.Success(albumDetails))
            }, { error ->

                callback(ResponseResult.Error(error))

            })

        compositeDisposable.add(disposable)
    }


}