package ru.dim.repository.datasource.database

import ru.dim.model.entity.SearchResult

class RepositoryLocal (
    private val dataSourceLocal: IDataSourceLocal<List<SearchResult>>
) : IRepositoryLocal<List<SearchResult>> {

    override suspend fun saveToDatabase(searchResults: List<SearchResult>) {
        dataSourceLocal.saveToDatabase(searchResults)
    }

    override suspend fun getData(word: String): List<SearchResult> {
        return dataSourceLocal.getData(word)
    }

    override suspend fun getAll(): List<SearchResult> {
        return dataSourceLocal.getAll()
    }
}