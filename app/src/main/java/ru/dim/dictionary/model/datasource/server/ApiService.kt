package ru.dim.dictionary.model.datasource.server

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import ru.dim.dictionary.model.entity.SearchResult

interface ApiService {
    @GET("words/search")
    fun search (@Query("search") word: String): Observable<List<SearchResult>>
}