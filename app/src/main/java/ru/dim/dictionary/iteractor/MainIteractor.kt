package ru.dim.dictionary.iteractor

import io.reactivex.Observable
import ru.dim.dictionary.model.ViewState
import ru.dim.dictionary.contract.IInteractor
import ru.dim.dictionary.contract.IRepository
import ru.dim.dictionary.model.entity.SearchResult

class MainInteractor(
    private val remoteRepository: IRepository<List<SearchResult>>,
    private val localRepository: IRepository<List<SearchResult>>
) : IInteractor<ViewState> {
    override fun getData(word: String, isOnline: Boolean): Observable<ViewState> =
        if (isOnline) {
            remoteRepository.getData(word).map { ViewState.Success(it) }
        } else {
            localRepository.getData(word).map { ViewState.Success(it) }
        }
}