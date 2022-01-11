package com.kizadev.myapplication.data

import com.kizadev.myapplication.domain.dto.AlbumDetailsDto
import com.kizadev.myapplication.domain.dto.AlbumListDto
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
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
