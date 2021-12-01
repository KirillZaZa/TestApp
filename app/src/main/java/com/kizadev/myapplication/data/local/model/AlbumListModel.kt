package com.kizadev.myapplication.data.local.model

data class AlbumListModel(
    val albumList: List<AlbumItem>?
)

data class AlbumItem(
    val albumId: String,
    val albumName: String,
    val albumTrackCount: String,
    val albumGenre: String,
    val albumPhotoUrl: String,
    val albumPrice: String
)
