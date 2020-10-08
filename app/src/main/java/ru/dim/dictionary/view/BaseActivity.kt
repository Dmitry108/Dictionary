package ru.dim.dictionary.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.dim.dictionary.model.ViewState
import ru.dim.dictionary.contract.IPresenter
import ru.dim.dictionary.contract.IView

abstract class BaseActivity<T: ViewState> : AppCompatActivity(), IView {

    protected lateinit var presenter: IPresenter<T, IView>

    protected abstract fun createPresenter(): IPresenter<T, IView>
    abstract override fun renderData(viewState: ViewState)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = createPresenter()
    }

    override fun onStart() {
        super.onStart()
        presenter.attachView(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.detachView(this)
    }
}