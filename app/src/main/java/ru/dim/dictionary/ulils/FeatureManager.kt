package ru.dim.dictionary.ulils

import android.content.ComponentCallbacks
import android.content.Intent
import androidx.core.app.ActivityCompat.startActivityForResult
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import kotlinx.android.synthetic.main.activity_main.*
import ru.dim.dictionary.R
import ru.dim.dictionary.app.DictionaryApp
import ru.dim.utils.HISTORY_ACTIVITY_PATH
import ru.dim.utils.HISTORY_FEATURE_NAME
import java.lang.Exception

class FeatureManager(private val callback: FeatureCallback) {
    private val splitInstallManager by lazy {
        SplitInstallManagerFactory.create(DictionaryApp.getInstance().applicationContext)
    }

    fun installFeature() {
        val request = SplitInstallRequest
            .newBuilder()
            .addModule(HISTORY_FEATURE_NAME)
            .build()

        splitInstallManager
            .startInstall(request)
            .addOnSuccessListener { callback.onSuccess() }
            .addOnFailureListener { error -> callback.onFailure(error) }
    }

    interface FeatureCallback {
        fun onSuccess()
        fun onFailure(error: Exception)
    }
}