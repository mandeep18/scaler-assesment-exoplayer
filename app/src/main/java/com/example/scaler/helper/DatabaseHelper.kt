package com.example.scaler.helper

import androidx.lifecycle.LiveData
import com.example.scaler.database.AppDao
import com.example.scaler.model.VideoModel
import java.util.concurrent.Executor

class DatabaseHelper (private val appDao: AppDao?, private val ioExecutor: Executor) {

    fun deleteAllVideos(){
        ioExecutor.execute {
            appDao?.deleteAllVideos()
        }
    }

    fun subscribeToVideoList(): LiveData<MutableList<VideoModel>>?{
        return appDao?.getVideosList()
    }

    fun updateVideoViewed(videoModel: VideoModel){
        ioExecutor.execute {
            appDao?.insertVideo(videoModel)
        }
    }

}