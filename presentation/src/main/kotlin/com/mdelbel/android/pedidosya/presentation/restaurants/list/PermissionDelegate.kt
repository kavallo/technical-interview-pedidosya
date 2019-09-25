package com.mdelbel.android.pedidosya.presentation.restaurants.list

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.app.Activity
import android.content.pm.PackageManager.PERMISSION_GRANTED
import androidx.core.app.ActivityCompat.checkSelfPermission
import androidx.core.app.ActivityCompat.requestPermissions
import com.mdelbel.android.pedidosya.presentation.restaurants.list.PermissionDelegate.PermissionEvent.PermissionDenied
import com.mdelbel.android.pedidosya.presentation.restaurants.list.PermissionDelegate.PermissionEvent.PermissionGranted

internal class PermissionDelegate {

    companion object {
        private const val PERMISSION_REQUEST_CODE = 1
    }

    internal fun checkPermissionAndRequestItIfNeeded(
        viewModel: RestaurantsOnListViewModel,
        activity: Activity
    ) {
        when (checkSelfPermission(activity, ACCESS_FINE_LOCATION) == PERMISSION_GRANTED) {
            true -> viewModel.onPermissionEvent(PermissionGranted)
            false -> requestPermissions(activity)
        }
    }

    internal fun onRequestPermissionsResult(
        viewModel: RestaurantsOnListViewModel,
        requestCode: Int,
        grantResults: IntArray
    ) {
        if (requestCode != PERMISSION_REQUEST_CODE) return

        when (grantResults[0] == PERMISSION_GRANTED) {
            true -> viewModel.onPermissionEvent(PermissionGranted)
            else -> viewModel.onPermissionEvent(PermissionDenied)
        }
    }

    private fun requestPermissions(activity: Activity) {
        requestPermissions(activity, arrayOf(ACCESS_FINE_LOCATION), PERMISSION_REQUEST_CODE)
    }

    sealed class PermissionEvent {

        object PermissionGranted : PermissionEvent()
        object PermissionDenied : PermissionEvent()
    }
}