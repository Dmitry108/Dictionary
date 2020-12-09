package ru.dim.repository

interface IRepository<T> {
    suspend fun getData(word: String): T
}