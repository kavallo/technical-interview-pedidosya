package com.mdelbel.android.pedidosya.presentation.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.mdelbel.android.pedidosya.gateway.Failed
import com.mdelbel.android.pedidosya.gateway.Loaded
import com.mdelbel.android.pedidosya.gateway.Loading
import com.mdelbel.android.pedidosya.gateway.authentication.AuthenticationGateway
import com.mdelbel.android.pedidosya.gateway.location.UserLocationProvider
import com.mdelbel.android.pedidosya.presentation.splash.ConditionStatus.*

class PreConditionMonitorViewModel(
    private val authenticationGateway: AuthenticationGateway,
    private val userLocationProvider: UserLocationProvider
) : ViewModel() {

    private var generalCondition = GeneralCondition(token = WAITING, location = WAITING)

    private val _preConditions = MediatorLiveData<GeneralCondition>()
    internal val preConditions: LiveData<GeneralCondition> get() = _preConditions

    internal fun obtainAccessToken() {
        _preConditions.addSource(authenticationGateway.authenticate()) { authState ->
            generalCondition = when (authState) {
                is Loading -> generalCondition.copy(token = WAITING)
                is Loaded -> generalCondition.copy(token = READY)
                is Failed -> generalCondition.copy(token = FAILED)
            }
            _preConditions.postValue(generalCondition)
        }
    }

    internal fun onPermissionGranted() {
        _preConditions.addSource(userLocationProvider.fetchLastKnown()) { locationState ->
            generalCondition = when (locationState) {
                is Loading -> generalCondition.copy(location = WAITING)
                is Loaded -> generalCondition.copy(location = READY)
                is Failed -> generalCondition.copy(location = FAILED)
            }
            _preConditions.postValue(generalCondition)
        }
    }

    internal fun onPermissionDenied() {
        generalCondition = generalCondition.copy(location = FAILED)
        _preConditions.postValue(generalCondition)
    }
}
