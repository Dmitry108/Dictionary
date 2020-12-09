package ru.dim.repository.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DatabaseEntity::class, MeaningEntity::class], version = 1, exportSchema = false)
abstract class LocalDatabase : RoomDatabase(){
    abstract fun getLocalDAO() : LocalDAO
}
