package ru.dim.dictionary.model.datasource.database

import androidx.room.*

@Entity(indices = [Index(value = ["text"], unique = true)],
    foreignKeys = [ForeignKey(entity = DatabaseEntity::class, parentColumns = ["id"], childColumns = ["text_id"])])
class MeaningEntity (
    @field:PrimaryKey(autoGenerate = true)
    @field:ColumnInfo(name = "id") var  id: Int,
    @field:ColumnInfo(name = "text_id") var text_id: Int,
    @field:ColumnInfo(name = "text") var text: String,
    @field:ColumnInfo(name = "partOfSpeechCode") var partOfSpeechCode: String,
    @field:ColumnInfo(name = "translation") var translation: String,
    @field:ColumnInfo(name = "transcription") var transcription: String,
    //@Embedded
)