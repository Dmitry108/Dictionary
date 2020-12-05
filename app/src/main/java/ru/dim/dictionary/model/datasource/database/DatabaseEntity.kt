package ru.dim.dictionary.model.datasource.database

import androidx.room.*

@Entity(indices = [Index(value = ["text"], unique = true)])
class DatabaseEntity (
    @field:PrimaryKey(autoGenerate = true)
    @field:ColumnInfo(name = "id") var id: Int,
    @field:ColumnInfo(name = "text") var text: String
)