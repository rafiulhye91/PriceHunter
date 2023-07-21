package com.example.pricehunter.data.local

import androidx.room.*
import com.example.pricehunter.data.local.model.SampleDbData

@Dao
interface DaoServices {
    @Query("SELECT * FROM sampledbdata")
    suspend fun getAllSampleDbData(): List<SampleDbData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSampleDbData(SampleDbData: SampleDbData): Long

    @Delete
    suspend fun deleteSampleDbData(SampleDbData: SampleDbData)

    @Update
    suspend fun updateSampleDbData(SampleDbData: SampleDbData)
}