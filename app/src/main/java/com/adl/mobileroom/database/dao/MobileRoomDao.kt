package com.adl.mobileroom.database.dao

import androidx.room.*
import com.adl.mobileroom.database.model.MobileModel

@Dao
interface MobileRoomDao {

    @Insert
    fun insertMobile(mobileRoom: MobileModel)

    @Update
    fun updateMobile(mobileRoom: MobileModel)

    @Delete
    fun deleteModel(mobileRoom: MobileModel)

    @Query("SELECT * FROM MobileModel")
    fun getAll(): List<MobileModel>

}