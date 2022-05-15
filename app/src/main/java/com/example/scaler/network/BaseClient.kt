package com.example.scaler.network

import com.example.scaler.AppController
import com.example.scaler.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

interface BaseClient {
    companion object{
        operator fun invoke(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(
                    OkHttpClient.Builder()
                    .addInterceptor(Interceptor {
                        it.proceed(it.request().newBuilder()
                            .build())
                    })
                    .addInterceptor(Interceptor {
                        //LogHelper.debug("TAG","CloudService** - ${}")
                        it.proceed(it.request()).let { response ->
                            if((response.code==401)){
                               //Logout
                            }
                            response
                        }
                    })
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        }

        fun<T> getApiService(service: Class<T>):T{
            if(AppController.baseClient!=null){
                return AppController.baseClient!!.create(service)
            }else{
                throw Throwable("Cannot be null")
            }
        }

    }

}