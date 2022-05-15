package com.example.scaler

import android.app.Application
import android.content.res.Configuration
import com.example.scaler.database.BaseAppDatabase
import com.example.scaler.helper.DatabaseHelper
import com.example.scaler.helper.LogHelper
import com.example.scaler.network.BaseClient
import retrofit2.Retrofit
import java.util.concurrent.Executors

class AppController:Application() {

    companion object{
        val TAG:String = AppController::class.java.simpleName
        var appController:AppController? = null
        var baseClient:Retrofit? = null
        var databaseHelper: DatabaseHelper? = null
    }

    override fun onCreate() {
        super.onCreate()

        appController = this

        databaseHelper = DatabaseHelper(appDao = BaseAppDatabase.getInstance(this).appDao(),ioExecutor = Executors.newSingleThreadExecutor())

        baseClient = BaseClient()

    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        LogHelper.debug(TAG, "onConfigurationChanged")
    }

    override fun onLowMemory() {
        super.onLowMemory()
        LogHelper.debug(TAG, "onLowMemory")
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        LogHelper.debug(TAG, "onTrimMemory")
    }

    override fun onTerminate() {
        LogHelper.debug(TAG, "onTerminate")
        super.onTerminate()
    }

}