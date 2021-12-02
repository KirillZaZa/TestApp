package com.kizadev.myapplication.presentation.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.kizadev.myapplication.presentation.listeners.OnItemClick

abstract class ViewHolder<T>(itemView: View): RecyclerView.ViewHolder(itemView){

    abstract fun bind(model: T)

}