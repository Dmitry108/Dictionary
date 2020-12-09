package ru.dim.dictionary.depricated

import ru.dim.model.ViewState

interface IView {
    fun renderData(viewState: ViewState)
}