package com.example.scaler.repository

import com.example.scaler.model.VideoListModel
import com.example.scaler.network.api.BaseRepository
import com.example.scaler.network.api.VideoApiService

class VideoRepository(private val videoApiService: VideoApiService) : BaseRepository() {

    suspend fun getVideoList(): VideoListModel? {
        return doSafeAPIRequest(call = { videoApiService.getVideoList() })
    }

}