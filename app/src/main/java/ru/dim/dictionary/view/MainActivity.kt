package ru.dim.dictionary.view

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import geekbrains.ru.translator.view.main.SearchDialogFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import ru.dim.dictionary.R
import ru.dim.dictionary.model.ViewState
import ru.dim.dictionary.model.entity.SearchResult
import ru.dim.dictionary.viewmodel.MainViewModel

class MainActivity : BaseActivity<ViewState>() {

    companion object {
        private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG = "bottom sheet tag"
    }

    override val viewModel: MainViewModel by viewModel()

    private var adapter: MainRecyclerViewAdapter? = null

    private val onListItemClickListener: MainRecyclerViewAdapter.OnListItemClickListener =
        object : MainRecyclerViewAdapter.OnListItemClickListener {
            override fun onItemClick(data: SearchResult) {
                Toast.makeText(this@MainActivity, data.text, Toast.LENGTH_SHORT).show()
            }
        }

    @ExperimentalCoroutinesApi
    private val onButtonClickListener = View.OnClickListener{
        val searchDialogFragment = SearchDialogFragment.newInstance()
        searchDialogFragment.setOnSearchClickListener(object : SearchDialogFragment.OnSearchClickListener {
            override fun onClick(searchWord: String) {
                viewModel.getData(searchWord, true)
            }
        })
        searchDialogFragment.show(supportFragmentManager, BOTTOM_SHEET_FRAGMENT_DIALOG_TAG)
    }

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launch{
            viewModel.getChannel().consumeEach {
                renderData(it)
            }
        }

        searchFab.setOnClickListener(onButtonClickListener)
    }

    override fun renderData(viewState: ViewState) {
        when (viewState) {
            is ViewState.Success -> onSuccess(viewState.data)
            is ViewState.Loading -> showViewLoading(viewState.process)
            is ViewState.Error -> showErrorScreen(viewState.error.message)
        }
    }

    private fun onSuccess(data: List<SearchResult>?) {
        if (data == null || data.isEmpty()) {
            showErrorScreen(getString(R.string.empty_server_response_on_success))
        } else {
            showViewSuccess()
            if (adapter == null) {
                mainRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
                mainRecyclerView.adapter = MainRecyclerViewAdapter(data, onListItemClickListener)
            } else {
                adapter!!.setData(data)
            }
        }
    }

    private fun showErrorScreen(error: String?) {
        showViewError()
        errorTextView.text = error ?: getString(R.string.undefined_error)
        reloadButton.setOnClickListener {
            viewModel.getData("hi", true)
        }
    }

    private fun showViewSuccess() {
        successLayout.visibility = VISIBLE
        loadingLayout.visibility = GONE
        errorLayout.visibility = GONE
    }

    private fun showViewLoading(progress: Int?) {
        successLayout.visibility = GONE
        loadingLayout.visibility = VISIBLE
        errorLayout.visibility = GONE
//        if (progress != null) {
//            progress_bar_horizontal.visibility = VISIBLE
//            progress_bar_round.visibility = GONE
//            progress_bar_horizontal.progress = dataModel.progress
//        } else {
//            progress_bar_horizontal.visibility = GONE
//            progress_bar_round.visibility = VISIBLE
//        }
//    }
    }

    private fun showViewError() {
        successLayout.visibility = GONE
        loadingLayout.visibility = GONE
        errorLayout.visibility = VISIBLE
    }
}