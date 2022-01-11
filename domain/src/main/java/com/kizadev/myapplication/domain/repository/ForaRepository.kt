package com.kizadev.myapplication.domain.repository

import com.kizadev.myapplication.domain.dto.AlbumDetailsDto
import com.kizadev.myapplication.domain.dto.AlbumListDto
import io.reactivex.Flowable

interface ForaRepository {

    fun getAlbumList(searchQuery: String): Flowable<AlbumListDto>

    fun getAlbumDetails(collectionId: String): Flowable<AlbumDetailsDto>
}
