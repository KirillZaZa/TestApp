package com.kizadev.myapplication.presentation.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kizadev.myapplication.data.local.model.AlbumItem
import com.kizadev.myapplication.data.local.model.TrackItem
import com.kizadev.myapplication.databinding.AlbumItemBinding
import com.kizadev.myapplication.databinding.TrackItemBinding

class AlbumViewHolder(
    itemView: View
) : ViewHolder<AlbumItem>(itemView) {

    private val viewBinding by viewBinding(AlbumItemBinding::bind)

    override fun bind(model: AlbumItem) {
        with(viewBinding){
            tvAlbumName.text = model.albumName
            tvCountOfTracks.text = model.albumTrackCount
            tvAlbumGenre.text = model.albumGenre
        }
    }


}

class TrackViewHolder(
    itemView: View
) : ViewHolder<TrackItem>(itemView){


    private val viewBinding by viewBinding(TrackItemBinding::bind)

    override fun bind(model: TrackItem) {

    }


}

class ItemOffsetDecoration: RecyclerView.ItemDecoration(){

    private val offsetY = 8

}