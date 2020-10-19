package ru.dim.dictionary.model.datasource.database

import ru.dim.dictionary.model.datasource.IDataSource
import ru.dim.dictionary.model.entity.SearchResult

class RoomProvider :
    IDataSource<List<SearchResult>> {
    override suspend fun getData(word: String): List<SearchResult> = listOf()
}