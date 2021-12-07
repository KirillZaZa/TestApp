package com.kizadev.myapplication.data.mapper

import com.kizadev.myapplication.data.local.model.AlbumDetailsModel
import com.kizadev.myapplication.data.local.model.AlbumItem
import com.kizadev.myapplication.data.local.model.AlbumListModel
import com.kizadev.myapplication.data.local.model.TrackItem
import com.kizadev.myapplication.data.network.dto.AlbumDetailsDto
import com.kizadev.myapplication.data.network.dto.AlbumListDto
import com.kizadev.myapplication.extensions.mapToMinutes

fun AlbumListDto.Result.mapToAlbumItem() = AlbumItem(
    albumId = "${this.collectionId}",
    albumGenre = buildString {
        append("Жанр: ")
        append(primaryGenreName)
    },
    albumName = this.collectionName,
    albumTrackCount = buildString {
        append("Песен: ")
        append(trackCount)
    },
    albumPrice = "${this.collectionPrice}",
    albumPhotoUrl = this.artworkUrl100 ?: ""
)

fun AlbumDetailsDto.Result.mapToTrackItem() = TrackItem(
    trackName = this.trackName.checkDetailsNotNull(),
    trackTime = this.trackTimeMillis?.mapToMinutes().checkDetailsNotNull(),
    trackPrice = this.trackPrice.checkDetailsNotNull(),
    trackPhotoUrl = this.artworkUrl100.checkDetailsNotNull()
)

fun AlbumDetailsDto.mapToAlbumDetailsModel(): AlbumDetailsModel {
    val trackList = this.results?.map {
        it.mapToTrackItem()
    }?.drop(1)

    return AlbumDetailsModel(
        albumTracksList = trackList,
    )
}

fun AlbumListDto.mapToAlbumListModel(): AlbumListModel {
    val albumList = this.results?.map { it.mapToAlbumItem() }
        ?.sortedBy {
            it.albumName
        }?.toMutableList()

    return AlbumListModel(albumList)
}

private fun Any?.checkDetailsNotNull(): String {
    return when (this) {
        null -> "Нет информации"
        else -> this.toString()
    }
}
