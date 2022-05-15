package com.example.scaler.listener

import android.view.View
import com.example.scaler.custom.CustomViewHolder

interface ViewAttachListener<T> {
    fun onViewAttachedToWindow(holder: CustomViewHolder<T>, itemView:View, adapterPosition:Int)
    fun onViewDetachedFromWindow(holder: CustomViewHolder<T>, itemView:View, adapterPosition: Int)
}