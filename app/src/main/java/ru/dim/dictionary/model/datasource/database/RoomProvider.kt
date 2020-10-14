package ru.dim.dictionary.model.datasource.database

import io.reactivex.Observable
import ru.dim.dictionary.model.datasource.IDataSource
import ru.dim.dictionary.model.entity.SearchResult

class RoomProvider :
    IDataSource<List<SearchResult>> {
    override fun getData(word: String): Observable<List<SearchResult>> = Observable.empty()
}