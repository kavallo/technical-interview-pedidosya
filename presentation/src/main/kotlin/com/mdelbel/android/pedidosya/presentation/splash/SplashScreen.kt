package com.mdelbel.android.pedidosya.presentation.splash

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.koin.android.viewmodel.ext.android.viewModel

class SplashScreen : Fragment() {

    companion object {
        private const val PERMISSION_REQUEST_CODE = 1
    }

    private val preConditionMonitorViewModel by viewModel<PreConditionMonitorViewModel>()

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState == null) {
            preConditionMonitorViewModel.obtainAccessToken()
            checkPermissionAndRequestItIfNeeded()
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity!!)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode != PERMISSION_REQUEST_CODE) return
        // TODO si ya le dio al no pedir permisos checkbox no pedirlos

        when (grantResults[0] == PERMISSION_GRANTED) {
            true -> preConditionMonitorViewModel.onPermissionGranted()
            else -> preConditionMonitorViewModel.onPermissionDenied()
        }
    }

    private fun checkPermissionAndRequestItIfNeeded() {
        when (checkSelfPermission(activity!!, ACCESS_FINE_LOCATION) == PERMISSION_GRANTED) {
            true -> preConditionMonitorViewModel.onPermissionGranted()
            false -> requestPermissions(arrayOf(ACCESS_FINE_LOCATION), PERMISSION_REQUEST_CODE)
        }
    }

    private fun observeViewState() {
        preConditionMonitorViewModel.viewState.observe(this, Observer { viewState ->
            when (viewState) {
                is PermissionGrantedState -> requestLocation()
            }
        })
    }

    private fun requestLocation() {
        fusedLocationClient.lastLocation.addOnSuccessListener(activity!!) { location ->
            preConditionMonitorViewModel.onLocationUpdated(location)
        }
    }
}