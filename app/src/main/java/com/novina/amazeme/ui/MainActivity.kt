package com.novina.amazeme.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.novina.amazeme.R
import com.novina.amazeme.util.ConnectivityManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var connectivityManager: ConnectivityManager
    private var snackBar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        subscribeToConnectivityLiveData()
    }

    override fun onStart() {
        super.onStart()
        connectivityManager.registerConnectionObserver(this)
    }

    override fun onStop() {
        super.onStop()
        connectivityManager.unregisterConnectionObserver(this)
    }

    private fun subscribeToConnectivityLiveData() {
        connectivityManager.isNetworkAvailable.observe(this, { isConnected ->
            snackBar?.dismiss()
            if (!isConnected) {
                snackBar = Snackbar.make(
                    findViewById(R.id.layout_container),
                    R.string.no_internet_available_msg_snackbar,
                    Snackbar.LENGTH_INDEFINITE
                )
                snackBar?.show()
            }
        })
    }
}