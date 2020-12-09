package ru.dim.repository.datasource.server

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query
import ru.dim.model.entity.SearchResult

interface ApiService {
    @GET("words/search")
    fun searchAsync (@Query("search") word: String): Deferred<List<SearchResult>>
}