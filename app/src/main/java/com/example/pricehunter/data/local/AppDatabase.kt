package com.example.pricehunter.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pricehunter.data.local.model.SampleDbData

@Database(
    entities = [SampleDbData::class],
    version = 1
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun getDaoServices(): DaoServices

    companion object {
        const val DATABASE_NAME = "sample_db"
    }
}