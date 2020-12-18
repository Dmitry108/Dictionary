package ru.dim.dictionary.view.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.loading_layout.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import org.koin.android.scope.currentScope
import ru.dim.core.base.BaseActivity
import ru.dim.dictionary.R
import ru.dim.dictionary.di.injectDependencies
import ru.dim.dictionary.ulils.FeatureManager
import ru.dim.dictionary.ulils.UpdateManager
import ru.dim.dictionary.ulils.UpdateManager.UpdateCallback
import ru.dim.dictionary.ulils.isOnline
import ru.dim.dictionary.view.description.DescriptionActivity
import ru.dim.dictionary.viewmodel.MainViewModel
import ru.dim.model.ViewState
import ru.dim.model.entity.SearchResult
import ru.dim.utils.*
import java.lang.Exception

class MainActivity : BaseActivity<ViewState>() {

    companion object {
        private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG = "bottom sheet tag"
    }

    init {
        injectDependencies()
    }

    override val viewModel: MainViewModel by currentScope.inject()

    private var adapter: MainRecyclerViewAdapter? = null

    private val onListItemClickListener: MainRecyclerViewAdapter.OnListItemClickListener =
        object : MainRecyclerViewAdapter.OnListItemClickListener {
            override fun onItemClick(data: SearchResult) {
                viewModel.saveCurrentResult(data)
                startActivity(Intent(this@MainActivity, DescriptionActivity::class.java))
            }
        }

    @ExperimentalCoroutinesApi
    private val onButtonClickListener = View.OnClickListener{
        val searchDialogFragment = SearchDialogFragment.newInstance()
        searchDialogFragment.setOnSearchClickListener(object : SearchDialogFragment.OnSearchClickListener {
            override fun onClick(searchWord: String) {
                viewModel.getData(searchWord, isOnline())
            }
        })
        searchDialogFragment.show(supportFragmentManager,
            BOTTOM_SHEET_FRAGMENT_DIALOG_TAG
        )
    }

    private val updateManager: UpdateManager by lazy {
        UpdateManager(callback = object : UpdateCallback{
            override fun onDownloaded() {
                Snackbar.make(mainActivity, resources.getString(R.string.update_is_downloaded), Snackbar.LENGTH_INDEFINITE).apply {
                    setAction("RESTART") { updateManager.completeUpdate() }
                    show()
                }
            }})
    }

    private val featureManager: FeatureManager by lazy {
        FeatureManager(callback = object : FeatureManager.FeatureCallback {
            override fun onSuccess() {
                val intent = Intent().setClassName(packageName, HISTORY_ACTIVITY_PATH)
                startActivityForResult(intent, HISTORY_REQUEST_CODE)
            }
            override fun onFailure(error: Exception) {
                Snackbar.make(mainActivity, "${resources.getString(R.string.could_not_download_feature)}: ${error.message}", Snackbar.LENGTH_LONG).show()
            }
        })
    }

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        updateManager.checkForUpdate(this)

        lifecycleScope.launch{
            viewModel.getChannel().consumeEach {
                renderData(it)
            }
        }
        searchFab.setOnClickListener(onButtonClickListener)
    }

    override fun onResume() {
        super.onResume()
        updateManager.onResume(this)
    }

    @ExperimentalCoroutinesApi
    override fun renderData(viewState: ViewState) {
        when (viewState) {
            is ViewState.Success<*> -> onSuccess(viewState.data as? List<SearchResult>)
            is ViewState.Loading -> showViewLoading(viewState.process)
            is ViewState.Error -> showErrorScreen(viewState.error.message)
        }
    }

    @ExperimentalCoroutinesApi
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

    @ExperimentalCoroutinesApi
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.menu_main -> {
                featureManager.installFeature()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    @ExperimentalCoroutinesApi
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == HISTORY_REQUEST_CODE && resultCode == HISTORY_RESULT_CODE) {
            viewModel.showData()
        }
        if (requestCode == UPDATE_REQUEST_CODE) {
            if (resultCode == UPDATE_RESULT_CODE) { updateManager.unregisterListener() }
            else {
                Snackbar.make(mainActivity, "${resources.getString(R.string.update_was_failed)} $resultCode", Snackbar.LENGTH_LONG).show()
            }
        }
    }
}