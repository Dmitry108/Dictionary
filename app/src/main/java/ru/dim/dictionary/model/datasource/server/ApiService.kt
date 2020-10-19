package ru.dim.dictionary.model.datasource.server

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query
import ru.dim.dictionary.model.entity.SearchResult

interface ApiService {
    @GET("words/search")
    fun searchAsync (@Query("search") word: String): Deferred<List<SearchResult>>
}