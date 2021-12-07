package com.kizadev.myapplication.presentation.adapters

sealed class ItemType {

    abstract val itemType: Int

    data class AlbumItem(override val itemType: Int = 0) : ItemType()

    data class TrackIem(override val itemType: Int = 1) : ItemType()
}
