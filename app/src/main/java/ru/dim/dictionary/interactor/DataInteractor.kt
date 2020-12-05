package ru.dim.dictionary.interactor

import ru.dim.dictionary.model.ViewState
import ru.dim.dictionary.model.datasource.database.IRepositoryLocal
import ru.dim.dictionary.model.repository.IRepository
import ru.dim.dictionary.model.entity.SearchResult

class DataInteractor (
    private val remoteRepository: IRepository<List<SearchResult>>,
    private val localRepository: IRepositoryLocal<List<SearchResult>>
) : IDataInteractor<ViewState> {

    override var currentResult: SearchResult? = null

    override suspend fun getData(word: String, isOnline: Boolean): ViewState
    {
        val viewState: ViewState
        if (isOnline) {
            val data = remoteRepository.getData(word)
            viewState = ViewState.Success(data)
            localRepository.saveToDatabase(data)
        } else {
            viewState = ViewState.Success(localRepository.getData(word))
        }
        return viewState
    }

    override suspend fun getAll(): ViewState = ViewState.Success(localRepository.getAll())
}