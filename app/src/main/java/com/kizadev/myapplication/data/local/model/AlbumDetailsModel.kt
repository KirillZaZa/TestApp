package com.kizadev.myapplication.data.local.model

data class AlbumDetailsModel(
    val albumTracksList: List<TrackItem>?
)

data class TrackItem(
    val trackName: String,
    val trackTime: String,
    val trackPrice: String,
    val trackPhotoUrl: String
)
