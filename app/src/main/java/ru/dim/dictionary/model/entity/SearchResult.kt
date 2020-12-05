package ru.dim.dictionary.model.entity

import com.google.gson.annotations.SerializedName

data class SearchResult(
    @SerializedName("id") var id: Int,
    @SerializedName("text") var text: String,
    @SerializedName( "meanings") var meanings: List<Meaning>?
)

data class Meaning(
    @SerializedName("id") var id: Int,
    @SerializedName("partOfSpeechCode") var partOfSpeechCode: String,
    @SerializedName("translation") var translation: Translation,
    @SerializedName("previewUrl") var previewUrl: String,
    @SerializedName("imageUrl") var imageUrl: String,
    @SerializedName("transcription") var transcription: String,
    @SerializedName("soundUrl") var soundUrl: String
)

data class Translation(
    @SerializedName("text") var text: String,
    @SerializedName("note") var note: String
)