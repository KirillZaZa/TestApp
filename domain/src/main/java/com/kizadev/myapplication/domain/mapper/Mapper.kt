package com.kizadev.myapplication.domain.mapper

import com.kizadev.myapplication.domain.dto.AlbumDetailsDto
import com.kizadev.myapplication.domain.dto.AlbumListDto
import com.kizadev.myapplication.domain.model.AlbumDetailsModel
import com.kizadev.myapplication.domain.model.AlbumItem
import com.kizadev.myapplication.domain.model.AlbumListModel
import com.kizadev.myapplication.domain.model.TrackItem
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.concurrent.TimeUnit

fun AlbumListDto.Result.mapToAlbumItem(randomCoefficient: Double) = AlbumItem(
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
    albumPrice = this.collectionPrice.toCompatiblePrice(),
    albumPhotoUrl = this.artworkUrl100 ?: "",
    albumSecondPrice = (this.collectionPrice * randomCoefficient).toCompatiblePrice()
)

fun AlbumDetailsDto.Result.mapToTrackItem(randomCoefficient: Double) = TrackItem(
    trackName = this.trackName.checkDetailsNotNull(),
    trackTime = this.trackTimeMillis?.mapToMinutes().checkDetailsNotNull(),
    trackPrice = this.trackPrice.checkDetailsNotNull(),
    trackPhotoUrl = this.artworkUrl100.checkDetailsNotNull(),
    trackSecondPrice = (
        (this.trackPrice ?: 1.0) * randomCoefficient
        ).toCompatiblePrice()
)

fun Double.toCompatiblePrice(): String {
    val decimalFormatSymbols = DecimalFormatSymbols().apply {
        decimalSeparator = '.'
    }
    val formattedValue = DecimalFormat("#.##", decimalFormatSymbols).format(this)

    return buildString {
        append(formattedValue)
    }
}

fun AlbumDetailsDto.mapToAlbumDetailsModel(randomCoefficient: Double): AlbumDetailsModel {
    val trackList = this.results?.map {
        it.mapToTrackItem(randomCoefficient)
    }?.drop(1)

    return AlbumDetailsModel(
        albumTracksList = trackList,
    )
}

fun AlbumListDto.mapToAlbumListModel(randomCoefficient: Double): AlbumListModel {
    val albumList = this.results?.map { it.mapToAlbumItem(randomCoefficient) }
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
