package ru.dim.dictionary.model.datasource.database

import ru.dim.dictionary.model.entity.SearchResult
import ru.dim.dictionary.model.repository.IRepository

interface IRepositoryLocal<T> : IRepository<T> {
    suspend fun saveToDatabase(searchResults: List<SearchResult>)
    suspend fun getAll(): List<SearchResult>
}