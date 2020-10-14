package ru.dim.dictionary.model.datasource.server

import io.reactivex.Observable
import ru.dim.dictionary.model.datasource.IDataSource
import ru.dim.dictionary.model.entity.SearchResult

class DataSourceRemote(
    private val remoteProvider: RetrofitProvider = RetrofitProvider()
) : IDataSource<List<SearchResult>> {

    override fun getData(word: String): Observable<List<SearchResult>> =
        remoteProvider.getData(word)
}