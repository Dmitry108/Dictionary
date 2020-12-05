package ru.dim.dictionary.view.description

import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_description.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import ru.dim.dictionary.R
import ru.dim.dictionary.model.ViewState
import ru.dim.dictionary.model.entity.SearchResult
import ru.dim.dictionary.view.base.BaseActivity
import ru.dim.dictionary.viewmodel.DescriptionViewModel

class DescriptionActivity : BaseActivity<ViewState>() {

    override val viewModel: DescriptionViewModel by viewModel()

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        description_layout.setOnRefreshListener { viewModel.showData() }

        lifecycleScope.launch{
            viewModel.getChannel().consumeEach { renderData(it) }
        }
        viewModel.showData()
    }

    override fun renderData(viewState: ViewState) {
        when(viewState){
            is ViewState.Success<*> -> showData(viewState.data as? SearchResult)
            is ViewState.Error -> showError(viewState.error.message)
            is ViewState.Loading -> showLoading()
        }
    }

    private fun showLoading() {
        description_layout.isRefreshing = true
    }

    private fun showError(message: String?){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        stopRefreshing()
    }

    private fun showData(data: SearchResult?){
        data?.let {
            description_header.text = data.text
            description_textview.text = data.meanings?.get(0)?.translation?.text
            val imageUrl = data.meanings?.get(0)?.imageUrl
            if (imageUrl.isNullOrBlank()) {
                stopRefreshing()
            } else {
                loadPicture(description_imageview, "https:$imageUrl" )
            }
        }
    }

    private fun stopRefreshing() {
        if (description_layout.isRefreshing) description_layout.isRefreshing = false
    }

    private fun loadPicture(imageView: ImageView, imageUrl: String) {
        Picasso.with(this)
            .load(imageUrl)
            .placeholder(R.drawable.ic_empty_image)
            .fit().centerCrop()
            .into(imageView, object : Callback{
                override fun onSuccess() {
                    stopRefreshing()
                }
                override fun onError() {
                    stopRefreshing()
                    imageView.setImageResource(R.drawable.ic_empty_image)
                }
            })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showDialog() {
        //            AlertDialogFragment.newInstance(
        //                getString(R.string.dialog_title_device_is_offline),
        //                getString(R.string.dialog_message_device_is_offline)
        //            ).show(
        //                supportFragmentManager,
        //                DIALOG_FRAGMENT_TAG
        //            )
    }
}