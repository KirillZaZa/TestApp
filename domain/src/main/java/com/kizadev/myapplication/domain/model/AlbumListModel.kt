package com.kizadev.myapplication.domain.model

import java.io.Serializable

data class AlbumListModel(
    val albumList: MutableList<AlbumItem>?
)

data class AlbumItem(
    val albumId: String,
    val albumName: String,
    val albumTrackCount: String,
    val albumGenre: String,
    val albumPhotoUrl: String,
    val albumPrice: String
) : Serializable
