package ru.dim.dictionary.interactor

import io.reactivex.Observable
import ru.dim.dictionary.model.ViewState
import ru.dim.dictionary.model.repository.IRepository
import ru.dim.dictionary.model.entity.SearchResult
import javax.inject.Inject

class MainInteractor @Inject constructor (
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