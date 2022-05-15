package com.example.scaler.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.scaler.helper.LogHelper
import com.example.scaler.model.VideoListModel
import com.example.scaler.network.BaseClient
import com.example.scaler.network.api.BaseViewModel
import com.example.scaler.network.api.VideoApiService
import com.example.scaler.repository.VideoRepository
import kotlinx.coroutines.launch

class VideoViewModel : BaseViewModel() {
    private val videoRepository = VideoRepository(BaseClient.getApiService(VideoApiService::class.java))

    internal val videoListLiveData = MutableLiveData<VideoListModel>()

    fun getVideoList(){
        scope.launch {
            try {
                videoListLiveData.postValue(videoRepository.getVideoList())
            } catch (e: Throwable){
                e.printStackTrace()
            }
        }
    }

}