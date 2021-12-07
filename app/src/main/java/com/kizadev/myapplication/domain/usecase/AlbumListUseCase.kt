package com.kizadev.myapplication.domain.usecase

import com.kizadev.myapplication.data.local.model.AlbumListModel
import com.kizadev.myapplication.domain.result_wrapper.ResponseResult
import io.reactivex.Flowable

interface AlbumListUseCase {

    fun getAlbums(searchQuery: String): Flowable<ResponseResult<AlbumListModel>>
}
