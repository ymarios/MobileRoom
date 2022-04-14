package com.adl.mobileroom.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.adl.mobileroom.database.dao.MobileRoomDao
import com.adl.mobileroom.database.model.MobileModel

@Database( version = 1,entities = [MobileModel::class])
abstract class MobileRoomDatabase:RoomDatabase() {

    abstract  fun mobileRoomDao():MobileRoomDao

    companion object{

        var instance:MobileRoomDatabase?=null

        @Synchronized
        fun getInstance(ctx: Context):MobileRoomDatabase{

            if(instance == null) {
                instance = Room.databaseBuilder(
                    ctx.applicationContext,
                    MobileRoomDatabase::class.java,
                    "db_mobileroom"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }

            return instance!!
        }

    }

}