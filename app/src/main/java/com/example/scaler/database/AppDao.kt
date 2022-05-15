package com.example.scaler.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.scaler.model.VideoModel

@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertVideo(videos: VideoModel)

    @Query("SELECT * from table_videos")
    fun getVideosList():LiveData<MutableList<VideoModel>>

    @Query("DELETE from table_videos")
    fun deleteAllVideos()
}