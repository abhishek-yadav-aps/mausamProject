package com.abhitom.mausamproject.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.abhitom.mausamproject.data.database.entity.CURRENT_LOCATION_ID
import com.abhitom.mausamproject.data.database.entity.ReverseGeoCodingApiResponse

@Dao
interface CurrentLocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(locationEntry: ReverseGeoCodingApiResponse)

    @Query("select * from current_location where id = $CURRENT_LOCATION_ID")
    fun getLocation(): LiveData<ReverseGeoCodingApiResponse>
}