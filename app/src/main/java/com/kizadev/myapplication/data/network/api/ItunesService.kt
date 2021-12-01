package com.kizadev.myapplication.data.network.api

import com.kizadev.myapplication.data.network.dto.AlbumDetailsDto
import com.kizadev.myapplication.data.network.dto.AlbumListDto
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface ItunesService {


    @GET("search")
    fun getAlbumList(
        @Query("term") searchQuery: String,
        @Query("entity") entity: String = "album",
        @Query("lang") lang: String = "ru"
    ): Flowable<AlbumListDto>


    @GET("lookup")
    fun getAlbumDetails(
        @Query("id") collectionId: String,
        @Query("entity") entity: String = "song"
    ): Flowable<AlbumDetailsDto>


}