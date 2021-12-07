package com.kizadev.myapplication.data.local.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class AlbumListModel(
    val albumList: MutableList<AlbumItem>?
)

@Parcelize
data class AlbumItem(
    val albumId: String,
    val albumName: String,
    val albumTrackCount: String,
    val albumGenre: String,
    val albumPhotoUrl: String,
    val albumPrice: String
) : Parcelable
