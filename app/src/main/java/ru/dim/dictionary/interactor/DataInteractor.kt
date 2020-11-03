package ru.dim.dictionary.interactor

import ru.dim.dictionary.model.ViewState
import ru.dim.dictionary.model.repository.IRepository
import ru.dim.dictionary.model.entity.SearchResult

class DataInteractor (
    private val remoteRepository: IRepository<List<SearchResult>>,
    private val localRepository: IRepository<List<SearchResult>>
) : IDataInteractor<ViewState> {

    var currentResult: SearchResult? = null

    override suspend fun getData(word: String, isOnline: Boolean): ViewState =
        ViewState.Success(
            (if (isOnline) {
                remoteRepository
            } else {
                localRepository
            }).getData(word))
}