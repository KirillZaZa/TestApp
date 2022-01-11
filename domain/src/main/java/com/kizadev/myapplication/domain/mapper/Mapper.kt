package com.kizadev.myapplication.domain.mapper

import com.kizadev.myapplication.domain.dto.AlbumDetailsDto
import com.kizadev.myapplication.domain.dto.AlbumListDto
import com.kizadev.myapplication.domain.model.AlbumDetailsModel
import com.kizadev.myapplication.domain.model.AlbumItem
import com.kizadev.myapplication.domain.model.AlbumListModel
import com.kizadev.myapplication.domain.model.TrackItem
import java.lang.StringBuilder
import java.util.concurrent.TimeUnit

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

fun Long.mapToMinutes(): String {
    val minutes = TimeUnit.MILLISECONDS.toMinutes(this)
    val seconds = TimeUnit.MILLISECONDS.toSeconds(this) - TimeUnit.MINUTES.toSeconds(minutes)

    return StringBuilder().append("$minutes:$seconds").toString()
}
