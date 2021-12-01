package com.kizadev.myapplication.data.repository

import com.kizadev.myapplication.data.network.api.ItunesService
import com.kizadev.myapplication.data.network.dto.AlbumDetailsDto
import com.kizadev.myapplication.data.network.dto.AlbumListDto
import com.kizadev.myapplication.data.repository.ForaRepository
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ForaRepositoryImpl @Inject constructor(
    private val iTunesApi: ItunesService
): ForaRepository {


    override fun getAlbumList(searchQuery: String): Flowable<AlbumListDto> {
        return iTunesApi.getAlbumList(searchQuery).subscribeOn(Schedulers.io())
    }

    override fun getAlbumDetails(collectionId: String): Flowable<AlbumDetailsDto> {
        return iTunesApi.getAlbumDetails(collectionId).subscribeOn(Schedulers.io())
    }


}