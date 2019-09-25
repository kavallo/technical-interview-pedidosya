package com.mdelbel.android.pedidosya.presentation.splash

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mdelbel.android.pedidosya.gateway.RequestState
import com.mdelbel.android.pedidosya.gateway.authentication.AuthenticationGateway

class PreConditionMonitorViewModel(private val authenticationGateway: AuthenticationGateway) :
    ViewModel() {

    private val _viewState = MutableLiveData<ViewState>()
    internal val viewState: LiveData<ViewState> get() = _viewState

    internal fun obtainAccessToken(): LiveData<RequestState> =
        authenticationGateway.authenticate() //TODO

    internal fun onPermissionGranted() {
        _viewState.postValue(PermissionGrantedState)
        TODO()
    }

    internal fun onPermissionDenied() {
        TODO()
    }

    fun onLocationUpdated(location: Location?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
