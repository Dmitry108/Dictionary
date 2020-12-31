package ru.dim.dictionary.ulils

import android.content.Intent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.ContentViewCallback
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.common.IntentSenderForResultStarter
import com.google.android.play.core.install.InstallState
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import ru.dim.dictionary.app.DictionaryApp
import ru.dim.utils.UPDATE_REQUEST_CODE

class UpdateManager(private val callback: UpdateCallback) {

    private val appUpdateManager: AppUpdateManager by lazy {
        AppUpdateManagerFactory.create(DictionaryApp.getInstance().applicationContext)
    }

    fun checkForUpdate(activity: AppCompatActivity) {
        appUpdateManager.appUpdateInfo.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE &&
                appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                appUpdateManager.registerListener { stateUpdateListener }
                appUpdateManager.startUpdateFlowForResult(appUpdateInfo, AppUpdateType.IMMEDIATE, activity, UPDATE_REQUEST_CODE)
            }
        }
    }

    fun onResume(activity: AppCompatActivity) {
        appUpdateManager.appUpdateInfo.addOnSuccessListener { appUpdateInfo ->
            checkDownloadStatus(appUpdateInfo.installStatus())
        }
    }

    private val stateUpdateListener = InstallStateUpdatedListener { installState ->
        checkDownloadStatus(installState.installStatus())
    }

    private fun checkDownloadStatus(installStatus: Int){
        if (installStatus == InstallStatus.DOWNLOADED) {
            callback.onDownloaded()
        }
    }

    fun completeUpdate() {
        appUpdateManager.completeUpdate()
    }

    fun unregisterListener() {
        appUpdateManager.unregisterListener { stateUpdateListener }
    }

    interface UpdateCallback {
        fun onDownloaded()
    }
}