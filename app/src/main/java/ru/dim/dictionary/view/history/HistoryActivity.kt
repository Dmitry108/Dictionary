package ru.dim.dictionary.view.history

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import ru.dim.dictionary.model.ViewState
import ru.dim.dictionary.view.base.BaseActivity
import org.koin.android.viewmodel.ext.android.viewModel
import ru.dim.dictionary.R
import ru.dim.dictionary.model.entity.SearchResult
import ru.dim.dictionary.utils.HISTORY_RESULT_CODE
import ru.dim.dictionary.viewmodel.HistoryViewModel

class HistoryActivity : BaseActivity<ViewState>() {

    override val viewModel: HistoryViewModel by viewModel()
    private val adapter by lazy { HistoryRecyclerViewAdapter(onListItemClickListener) }

    private val onListItemClickListener: HistoryRecyclerViewAdapter.OnListItemClickListener =
        object : HistoryRecyclerViewAdapter.OnListItemClickListener {
            override fun onItemClick(searchResult: SearchResult) {
                viewModel.saveCurrentResult(searchResult)
                setResult(HISTORY_RESULT_CODE)
                finish()
            }
        }

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        lifecycleScope.launch{
            viewModel.getChannel().consumeEach {
                renderData(it)
            }
        }
        historyRecyclerview.adapter = adapter
    }

    @ExperimentalCoroutinesApi
    override fun onResume() {
        super.onResume()
        viewModel.getAllData()
    }

    private fun setDataToAdapter(data: List<SearchResult>) {
        adapter.setData(data)
    }

    @ExperimentalCoroutinesApi
    override fun renderData(viewState: ViewState) {
        when (viewState) {
            is ViewState.Success<*> -> onSuccess(data = viewState.data as? List<SearchResult>)
//            is ViewState.Loading -> showViewLoading(viewState.process)
//            is ViewState.Error -> showErrorScreen(viewState.error.message)
        }
    }

    @ExperimentalCoroutinesApi
    private fun onSuccess(data: List<SearchResult>?) {
        Log.d("...", data.toString())
        data?.let {
            if(it.isEmpty()) {
                Toast.makeText(this, resources.getString(R.string.empty_server_response_on_success), Toast.LENGTH_LONG).show()
            } else {
                setDataToAdapter(data)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when(item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
    }
}