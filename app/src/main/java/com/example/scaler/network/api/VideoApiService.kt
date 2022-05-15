package com.example.scaler.network.api

import com.example.scaler.constants.APIConstants
import com.example.scaler.model.VideoListModel
import retrofit2.Response
import retrofit2.http.GET

interface VideoApiService {
    @GET(APIConstants.API_VIDEO_LIST)
    suspend fun getVideoList(): Response<VideoListModel>
}