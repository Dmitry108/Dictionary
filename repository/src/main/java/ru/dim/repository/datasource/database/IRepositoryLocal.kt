package ru.dim.repository.datasource.database

import ru.dim.model.entity.SearchResult
import ru.dim.repository.IRepository

interface IRepositoryLocal<T> : IRepository<T> {
    suspend fun saveToDatabase(searchResults: List<SearchResult>)
    suspend fun getAll(): List<SearchResult>
}