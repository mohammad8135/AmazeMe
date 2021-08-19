package com.novina.amazeme.util

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Connectivity Manager wrap the ConnectionLiveData
 * to ensure the availability of connectivity state
 *
 * i.e. Kill the application, turn on the airplane mode
 * and relaunching the app
 *
 */
@Singleton
class ConnectivityManager
@Inject
constructor(
    application: Application
) {
    private val connectionLiveData = ConnectionLiveData(application)

    // observe this in ui
    private val _isNetworkAvailable = MutableLiveData(false)
    val isNetworkAvailable: LiveData<Boolean>
        get() = _isNetworkAvailable

    fun registerConnectionObserver(lifecycleOwner: LifecycleOwner) {
        connectionLiveData.observe(lifecycleOwner, { isConnected ->
            isConnected?.let { _isNetworkAvailable.value = it }
        })
    }

    fun unregisterConnectionObserver(lifecycleOwner: LifecycleOwner) {
        connectionLiveData.removeObservers(lifecycleOwner)
    }

}