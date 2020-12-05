package ru.dim.dictionary.view.base

import androidx.appcompat.app.AppCompatActivity
import ru.dim.dictionary.model.ViewState
import ru.dim.dictionary.viewmodel.BaseViewModel

abstract class BaseActivity<T: ViewState> : AppCompatActivity() {

    abstract val viewModel: BaseViewModel<T>
    abstract fun renderData(viewState: T)
}