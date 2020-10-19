package ru.dim.dictionary.interactor

import ru.dim.dictionary.model.ViewState
import ru.dim.dictionary.model.repository.IRepository
import ru.dim.dictionary.model.entity.SearchResult

class MainInteractor (
    private val remoteRepository: IRepository<List<SearchResult>>,
    private val localRepository: IRepository<List<SearchResult>>
) : IInteractor<ViewState> {

    override suspend fun getData(word: String, isOnline: Boolean): ViewState =
        ViewState.Success(
            (if (isOnline) {
                remoteRepository
            } else {
                localRepository
            }).getData(word))
}