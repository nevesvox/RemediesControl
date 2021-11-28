package com.example.remediescontrol.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.remediescontrol.model.Remedy

@Database(entities = arrayOf(Remedy::class), version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun RemedyDao(): Dao
}