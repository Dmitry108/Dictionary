package ru.dim.dictionary.model.datasource.database

import io.reactivex.Observable
import ru.dim.dictionary.contract.IDataSource
import ru.dim.dictionary.model.entity.SearchResult

class DataSourceLocal(private val localProvider: RoomDataBaseImplementation = RoomDataBaseImplementation()
) : IDataSource<List<SearchResult>> {
    override fun getData(word: String): Observable<List<SearchResult>> = localProvider.getData(word)
}