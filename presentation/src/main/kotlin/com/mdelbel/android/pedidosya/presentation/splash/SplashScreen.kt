package com.mdelbel.android.pedidosya.presentation.splash

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
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
import org.koin.android.viewmodel.ext.android.viewModel

class SplashScreen : Fragment() {

    companion object {
        private const val PERMISSION_REQUEST_CODE = 1
    }

    private val preConditionMonitorViewModel by viewModel<PreConditionMonitorViewModel>()

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
        if (savedInstanceState == null) {
            preConditionMonitorViewModel.obtainAccessToken()
            checkPermissionAndRequestItIfNeeded()
        }
        preConditionMonitorViewModel.preConditions.observe(this, Observer {
            if (it.areConditionsMet())
                findNavController().navigate(R.id.action_splash_to_restaurantsOnList)
        })
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
}