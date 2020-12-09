package ru.dim.repository.datasource.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["text"], unique = true)])
class DatabaseEntity (
    @field:PrimaryKey(autoGenerate = true)
    @field:ColumnInfo(name = "id") var id: Int,
    @field:ColumnInfo(name = "text") var text: String
)