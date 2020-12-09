package ru.dim.dictionary.ulils

import android.content.Context
import android.net.ConnectivityManager
import ru.dim.dictionary.app.DictionaryApp

fun isOnline(): Boolean {
    val connectivityManager =
        DictionaryApp.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val netInfo = connectivityManager.activeNetworkInfo
    return netInfo != null && netInfo.isConnected
}

