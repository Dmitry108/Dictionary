package ru.dim.dictionary.model.datasource.database

import ru.dim.dictionary.model.entity.Meaning
import ru.dim.dictionary.model.entity.SearchResult
import ru.dim.dictionary.model.entity.Translation

class RoomProvider (private val localDAO: LocalDAO) :
    IDataSourceLocal<List<SearchResult>> {

    override suspend fun getData(word: String): List<SearchResult> =
        mapMeaningEntityToSearchResult(localDAO.getData(word))

    private fun mapMeaningEntityToSearchResult(meanings: List<MeaningEntity>): List<SearchResult> {
        val searchResults = ArrayList<SearchResult>()
        if (!meanings.isNullOrEmpty()) {
            for (entity in meanings){
                searchResults.add(
                    SearchResult(
                        id = entity.id,
                        text = entity.text,
                        meanings = listOf(Meaning(
                            id = entity.id,
                            partOfSpeechCode = entity.partOfSpeechCode,
                            translation = Translation(text = entity.translation, note = ""),
                            previewUrl = "",
                            imageUrl = "",
                            transcription = entity.transcription,
                            soundUrl = ""))
                    ))
        }}
        return searchResults
    }

    override suspend fun getAll(): List<SearchResult> =
        mapDatabaseEntityToSearchResult(localDAO.getAllWords())

    private fun mapDatabaseEntityToSearchResult(databaseEntities: List<DatabaseEntity>): List<SearchResult> {
        val appEntities = ArrayList<SearchResult>()
        if (!databaseEntities.isNullOrEmpty()) {
            for (entity in databaseEntities){
                appEntities.add(
                    SearchResult(
                        id = entity.id,
                        text = entity.text,
                        meanings = null))
            }
        }
        return appEntities
    }

    override suspend fun saveToDatabase(searchResults: List<SearchResult>) {
        mapSearchResultToDatabaseEntity(searchResults)
            ?.let {
            localDAO.insertWord(it)
        }
        mapSearchResultToMeaningEntities(searchResults)?.let{localDAO.insertAllMeanings(it)}
    }

    private fun mapSearchResultToDatabaseEntity(searchResults: List<SearchResult>): DatabaseEntity? {
        return DatabaseEntity(id = searchResults[0].id, text = searchResults[0].text)
    }
    private fun mapSearchResultToMeaningEntities(searchResults: List<SearchResult>): List<MeaningEntity>? {
        val meaningEntities = ArrayList<MeaningEntity>()
        if (!searchResults.isNullOrEmpty()) {
            for (entity in searchResults){
                meaningEntities.add(
                    MeaningEntity(
                        id = entity.id,
                        text_id = searchResults[0].id,
                        text = entity.text,
                        partOfSpeechCode = entity.meanings?.get(0)?.partOfSpeechCode ?: "",
                        translation = entity.meanings?.get(0)?.translation?.text ?: "",
                        transcription = entity.meanings?.get(0)?.transcription ?: "")
                )
            }
        }
        return meaningEntities
    }
}