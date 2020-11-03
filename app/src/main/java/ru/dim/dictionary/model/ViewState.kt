package ru.dim.dictionary.model

sealed class ViewState {
    class Success<T>(val data: T?): ViewState()
    class Error(val error: Throwable): ViewState()
    class Loading(val process: Int?): ViewState()
}