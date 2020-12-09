package ru.dim.repository.datasource.database

import androidx.room.*

@Dao
interface LocalDAO {

    @Query("SELECT * FROM DatabaseEntity")
    suspend fun getAllWords(): List<DatabaseEntity>

    @Query("SELECT * FROM MeaningEntity WHERE text_id = (SELECT id FROM DatabaseEntity WHERE text LIKE :word)")
    suspend fun getData(word: String): List<MeaningEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWord(entity: DatabaseEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllMeanings(entities: List<MeaningEntity>)

    @Update
    suspend fun update(entity: DatabaseEntity)

    @Delete
    suspend fun delete(entity: DatabaseEntity)
}