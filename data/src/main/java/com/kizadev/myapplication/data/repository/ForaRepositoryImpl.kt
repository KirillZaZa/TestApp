package com.kizadev.myapplication.data.repository

import com.kizadev.myapplication.data.ItunesService
import com.kizadev.myapplication.domain.dto.AlbumDetailsDto
import com.kizadev.myapplication.domain.dto.AlbumListDto
import com.kizadev.myapplication.domain.repository.ForaRepository
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ForaRepositoryImpl @Inject constructor(
    private val iTunesApi: ItunesService
) : ForaRepository {

    override fun getAlbumList(searchQuery: String): Flowable<AlbumListDto> {
        return iTunesApi.getAlbumList(searchQuery)
    }

    override fun getAlbumDetails(collectionId: String): Flowable<AlbumDetailsDto> {
        return iTunesApi.getAlbumDetails(collectionId).subscribeOn(Schedulers.io())
    }
}
