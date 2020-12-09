package ru.dim.repository.datasource.database

import ru.dim.model.entity.SearchResult
import ru.dim.repository.datasource.IDataSource

interface IDataSourceLocal<T> : IDataSource<T> {
    suspend fun saveToDatabase(searchResults: List<SearchResult>)
    suspend fun getAll(): List<SearchResult>
}