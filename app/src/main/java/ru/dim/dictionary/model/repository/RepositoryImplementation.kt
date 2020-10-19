package ru.dim.dictionary.model.repository

import ru.dim.dictionary.model.datasource.IDataSource
import ru.dim.dictionary.model.entity.SearchResult

class RepositoryImplementation(
    private val dataSource: IDataSource<List<SearchResult>>
) : IRepository<List<SearchResult>> {

    override suspend fun getData(word: String): List<SearchResult> =
        dataSource.getData(word)
}