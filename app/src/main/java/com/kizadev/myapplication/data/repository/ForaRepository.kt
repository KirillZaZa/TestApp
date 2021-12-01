package com.kizadev.myapplication.data.repository

import com.kizadev.myapplication.data.network.dto.AlbumDetailsDto
import com.kizadev.myapplication.data.network.dto.AlbumListDto
import io.reactivex.Flowable

interface ForaRepository {

    fun getAlbumList(searchQuery: String): Flowable<AlbumListDto>

    fun getAlbumDetails(collectionId: String): Flowable<AlbumDetailsDto>

}