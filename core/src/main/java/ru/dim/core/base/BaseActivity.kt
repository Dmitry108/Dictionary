package ru.dim.core.base

import androidx.appcompat.app.AppCompatActivity
import ru.dim.model.ViewState

abstract class BaseActivity<T: ViewState> : AppCompatActivity() {

    abstract val viewModel: BaseViewModel<T>
    abstract fun renderData(viewState: T)
}