package com.adl.mobileroom.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.adl.mobileroom.database.model.MobileModel

@Dao
interface MobileRoomDao {

    @Insert
    fun insertMobile(mobileRoom: MobileModel)

    @Delete
    fun deleteModel(mobileRoom: MobileModel)

    @Query("SELECT * FROM MobileModel")
    fun getAll(): List<MobileModel>

}