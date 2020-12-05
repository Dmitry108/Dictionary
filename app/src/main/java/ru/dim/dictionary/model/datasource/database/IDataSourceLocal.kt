package ru.dim.dictionary.model.datasource.database

import ru.dim.dictionary.model.datasource.IDataSource
import ru.dim.dictionary.model.entity.SearchResult

interface IDataSourceLocal<T> : IDataSource<T> {
    suspend fun saveToDatabase(searchResults: List<SearchResult>)
    suspend fun getAll(): List<SearchResult>
}