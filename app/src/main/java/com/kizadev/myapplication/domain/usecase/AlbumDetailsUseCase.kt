package com.kizadev.myapplication.domain.usecase

import com.kizadev.myapplication.data.local.model.AlbumDetailsModel
import com.kizadev.myapplication.domain.result_wrapper.ResponseResult

interface AlbumDetailsUseCase {

    fun getAlbumDetails(collectionId: String, callback: (ResponseResult<AlbumDetailsModel>) -> Unit)
}
