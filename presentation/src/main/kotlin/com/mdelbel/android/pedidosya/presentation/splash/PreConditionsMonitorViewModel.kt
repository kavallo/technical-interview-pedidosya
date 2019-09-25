package com.mdelbel.android.pedidosya.presentation.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.mdelbel.android.pedidosya.gateway.Failed
import com.mdelbel.android.pedidosya.gateway.Loaded
import com.mdelbel.android.pedidosya.gateway.Loading
import com.mdelbel.android.pedidosya.gateway.RequestState
import com.mdelbel.android.pedidosya.gateway.authentication.AuthenticationGateway
import com.mdelbel.android.pedidosya.gateway.location.UserLocationProvider
import com.mdelbel.android.pedidosya.presentation.splash.ConditionStatus.*

class PreConditionsMonitorViewModel(
    private val authenticationGateway: AuthenticationGateway,
    private val userLocationProvider: UserLocationProvider
) : ViewModel() {

    private var conditions =
        RestaurantsScreenConditions(AuthenticationCondition(WAITING), GeolocationCondition(WAITING))

    private val _preConditions = MediatorLiveData<RestaurantsScreenConditions>()
    internal val preConditions: LiveData<RestaurantsScreenConditions> get() = _preConditions

    internal fun obtainAccessToken() {
        _preConditions.addSource(authenticationGateway.authenticate()) { authState ->
            val conditionStatus = map(authState)
            conditions = conditions.copy(authentication = AuthenticationCondition(conditionStatus))
            _preConditions.postValue(conditions)
        }
    }

    internal fun onPermissionGranted() {
        _preConditions.addSource(userLocationProvider.fetchLastKnown()) { locationState ->
            val conditionStatus = map(locationState)
            conditions = conditions.copy(geolocation = GeolocationCondition(conditionStatus))
            _preConditions.postValue(conditions)
        }
    }

    internal fun onPermissionDenied() {
        conditions = conditions.copy(geolocation = GeolocationCondition(FAILED))
        _preConditions.postValue(conditions)
    }

    private fun map(requestState: RequestState): ConditionStatus {
        return when (requestState) {
            is Loading -> WAITING
            is Loaded -> READY
            is Failed -> FAILED
        }
    }
}