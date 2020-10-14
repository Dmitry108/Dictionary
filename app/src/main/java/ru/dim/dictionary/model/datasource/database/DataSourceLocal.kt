package ru.dim.dictionary.model.datasource.database

import io.reactivex.Observable
import ru.dim.dictionary.model.datasource.IDataSource
import ru.dim.dictionary.model.entity.SearchResult

class DataSourceLocal(private val localProvider: RoomProvider = RoomProvider()
) : IDataSource<List<SearchResult>> {
    override fun getData(word: String): Observable<List<SearchResult>> = localProvider.getData(word)
}