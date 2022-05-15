package com.example.scaler.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.scaler.model.VideoModel


@Database(entities = [VideoModel::class], exportSchema = false, version = 1)
abstract class BaseAppDatabase : RoomDatabase() {

    abstract fun appDao(): AppDao


    companion object {
        private val TAG = BaseAppDatabase::class.java.simpleName
        private val LOCK = Any()
        private const val DATABASE_NAME = "bhot_hard_scaler_db"
        private var mInstance: BaseAppDatabase? = null

        fun getInstance(context: Context): BaseAppDatabase {
            if (mInstance == null) {
                synchronized(LOCK) {
                    mInstance = Room.databaseBuilder(context.applicationContext, BaseAppDatabase::class.java, DATABASE_NAME)
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return mInstance!!
        }
    }

}