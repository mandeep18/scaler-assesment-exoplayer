package com.example.scaler.listener

import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.scaler.model.VideoModel

interface OnItemClickListener {
    fun onItemClick(videoModel: VideoModel, clMainVideoView: ImageView?)
}