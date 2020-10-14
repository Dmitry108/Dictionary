package ru.dim.dictionary.depricated

import ru.dim.dictionary.model.ViewState

interface IView {
    fun renderData(viewState: ViewState)
}