package ru.dim.dictionary.view.description

import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_description.*
import ru.dim.dictionary.R
import ru.dim.dictionary.utils.DESCRIPTION
import ru.dim.dictionary.utils.PICTURE_URL
import ru.dim.dictionary.utils.WORD
import ru.dim.dictionary.utils.isOnline

class DescriptionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        description_layout.setOnRefreshListener { refreshPicture() }
        description_layout.isRefreshing = true
        refreshPicture()
    }

    private fun refreshPicture() {
        if (isOnline(applicationContext)) {
            showData()
        } else {
            Toast.makeText(this, resources.getString(R.string.internetIsNotAvailable), Toast.LENGTH_SHORT).show()
//            showDialog()
            stopRefreshing()
        }
    }

    private fun showData(){
        val bundle = intent.extras
        bundle?.let {
            description_header.text = bundle.getString(WORD)
            description_textview.text = bundle.getString(DESCRIPTION)
            val imageUrl = bundle.getString(PICTURE_URL)
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