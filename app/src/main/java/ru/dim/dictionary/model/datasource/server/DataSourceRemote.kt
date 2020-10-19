package ru.dim.dictionary.model.datasource.server

import ru.dim.dictionary.model.datasource.IDataSource
import ru.dim.dictionary.model.entity.SearchResult

class DataSourceRemote (
    private val remoteProvider: RetrofitProvider = RetrofitProvider()
) : IDataSource<List<SearchResult>> {
    override suspend fun getData(word: String): List<SearchResult> =
        remoteProvider.getData(word)
}