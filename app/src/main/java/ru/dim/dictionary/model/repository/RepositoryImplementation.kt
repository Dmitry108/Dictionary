package ru.dim.dictionary.model.repository

import io.reactivex.Observable
import ru.dim.dictionary.model.datasource.IDataSource
import ru.dim.dictionary.model.entity.SearchResult

class RepositoryImplementation(
    private val dataSource: IDataSource<List<SearchResult>>
) : IRepository<List<SearchResult>> {

    override fun getData(word: String): Observable<List<SearchResult>> =
        dataSource.getData(word)
}