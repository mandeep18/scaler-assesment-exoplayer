package com.example.scaler.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class VideoListModel (
    @SerializedName("videos")
    var videos:ArrayList<VideoModel>? = null,
)

@Entity(tableName = "table_videos")
data class VideoModel (
    @PrimaryKey
    @SerializedName("video_url")
    var video_url:String,

    @SerializedName("description")
    var description:String? = null,

    @SerializedName("subtitle")
    var subtitle:String? = null,

    @SerializedName("thumb")
    var thumb:String? = null,

    @SerializedName("title")
    var title:String? = null,

    ):Serializable