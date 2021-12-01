package com.kizadev.myapplication.domain.usecase

import com.kizadev.myapplication.data.local.model.AlbumListModel
import com.kizadev.myapplication.domain.result_wrapper.ResponseResult

interface AlbumListUseCase {

    fun getAlbums(searchQuery: String, callback: (ResponseResult<AlbumListModel>) -> Unit)

}