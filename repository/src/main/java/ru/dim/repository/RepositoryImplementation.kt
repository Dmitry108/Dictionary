package ru.dim.repository

import ru.dim.model.entity.SearchResult
import ru.dim.repository.datasource.IDataSource

class RepositoryImplementation(
    private val dataSource: IDataSource<List<SearchResult>>
) : IRepository<List<SearchResult>> {

    override suspend fun getData(word: String): List<SearchResult> =
        dataSource.getData(word)
}