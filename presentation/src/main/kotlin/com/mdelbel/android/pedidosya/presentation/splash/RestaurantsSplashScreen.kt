package com.mdelbel.android.pedidosya.presentation.splash

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.mdelbel.android.pedidosya.presentation.R
import kotlinx.android.synthetic.main.screen_error.*
import org.koin.android.viewmodel.ext.android.viewModel

class RestaurantsSplashScreen : Fragment() {

    companion object {
        private const val PERMISSION_REQUEST_CODE = 3189
    }

    private val preConditionsMonitorViewModel by viewModel<PreConditionsMonitorViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.screen_splash, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        requestAccessToken()
        checkPermissionAndRequestItIfNeeded()

        preConditionsMonitorViewModel.preConditions.observe(this, Observer {
            it.executeIfConditions(
                areMet = {
                    findNavController().navigate(R.id.action_splash_to_restaurantsOnList)
                },
                onError = {
                    error_container.visibility = View.VISIBLE
                })
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode != PERMISSION_REQUEST_CODE) return

        when (grantResults[0] == PERMISSION_GRANTED) {
            true -> preConditionsMonitorViewModel.onPermissionGranted()
            else -> preConditionsMonitorViewModel.onPermissionDenied()
        }
    }

    private fun requestAccessToken() = preConditionsMonitorViewModel.obtainAccessToken()

    private fun checkPermissionAndRequestItIfNeeded() {
        when (checkSelfPermission(activity!!, ACCESS_FINE_LOCATION) == PERMISSION_GRANTED) {
            true -> preConditionsMonitorViewModel.onPermissionGranted()
            false -> requestPermissions(arrayOf(ACCESS_FINE_LOCATION), PERMISSION_REQUEST_CODE)
        }
    }
}