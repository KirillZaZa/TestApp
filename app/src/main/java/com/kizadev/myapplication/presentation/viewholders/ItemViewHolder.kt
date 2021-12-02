package com.kizadev.myapplication.presentation.viewholders

import android.graphics.Rect
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.size
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.kizadev.myapplication.R
import com.kizadev.myapplication.data.local.model.AlbumItem
import com.kizadev.myapplication.data.local.model.TrackItem
import com.kizadev.myapplication.databinding.AlbumItemBinding
import com.kizadev.myapplication.databinding.TrackItemBinding
import com.kizadev.myapplication.extensions.dpToPx
import com.kizadev.myapplication.presentation.listeners.OnItemClick

class AlbumViewHolder(
    itemView: View,
) : ViewHolder<AlbumItem>(itemView) {

    private val viewBinding by viewBinding(AlbumItemBinding::bind)


    override fun bind(model: AlbumItem) {
        val context = itemView.context
        with(viewBinding){

            tvAlbumName.text = model.albumName
            tvCountOfTracks.text = model.albumTrackCount
            tvAlbumGenre.text = model.albumGenre

            Glide.with(context)
                .load(model.albumPhotoUrl)
                .error(ContextCompat.getDrawable(context, R.drawable.ic_name_album_icon))
                .transition(DrawableTransitionOptions.withCrossFade(300))
                .into(ivImageAlbumItem)

        }

    }



}

class TrackViewHolder(
    itemView: View,

) : ViewHolder<TrackItem>(itemView){


    private val viewBinding by viewBinding(TrackItemBinding::bind)

    override fun bind(model: TrackItem) {

    }


}

class ItemOffsetDecoration: RecyclerView.ItemDecoration(){

    private val offsetY = 8
    private val offsetYLast = 24
    private val offsetYFirstElem = 32

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        when (parent.getChildAdapterPosition(view)) {
            0 -> {
                outRect.top = view.context.dpToPx(offsetYFirstElem)
            }

            parent.adapter!!.itemCount - 1 -> {

                outRect.top = view.context.dpToPx(offsetY)
                outRect.bottom = view.context.dpToPx(offsetYLast)

            }

            else -> {
                outRect.top = view.context.dpToPx(offsetY)
            }


        }
    }
}