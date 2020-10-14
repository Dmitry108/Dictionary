package ru.dim.dictionary.model

import ru.dim.dictionary.model.entity.SearchResult

sealed class ViewState {
    class Success(val data: List<SearchResult>?): ViewState()
    class Error(val error: Throwable): ViewState()
    class Loading(val process: Int?): ViewState()
}