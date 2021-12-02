package com.kizadev.myapplication.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kizadev.myapplication.R
import com.kizadev.myapplication.presentation.listeners.OnItemClick
import com.kizadev.myapplication.presentation.viewholders.AlbumViewHolder
import com.kizadev.myapplication.presentation.viewholders.TrackViewHolder
import com.kizadev.myapplication.presentation.viewholders.ViewHolder

class ItemRecyclerAdapter<T>(
    private val itemType: ItemType,
) : RecyclerView.Adapter<ViewHolder<T>>() {

    private lateinit var initList: MutableList<T>
    private var onItemListener: OnItemClick? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newList: MutableList<T>) {
        initList = newList
        notifyDataSetChanged()
    }

    fun setOnItemListener(listener: OnItemClick){
        onItemListener = listener
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder<T> {
        when (itemType) {
            is ItemType.AlbumItem -> {

                val view = LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.album_item, viewGroup, false)

                return AlbumViewHolder(view) as ViewHolder<T>
            }

            is ItemType.TrackIem -> {

                val view = LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.track_item, viewGroup, false)

                return TrackViewHolder(view) as ViewHolder<T>

            }
        }
    }

    override fun onBindViewHolder(viewHolder: ViewHolder<T>, position: Int) {
        val item = initList[position]

        viewHolder.bind(item)

        if (onItemListener != null){
            viewHolder.itemView.setOnClickListener {
                onItemListener!!.onItemClick(position)
            }
        }


    }

    override fun getItemCount(): Int {
        return initList.size
    }


}