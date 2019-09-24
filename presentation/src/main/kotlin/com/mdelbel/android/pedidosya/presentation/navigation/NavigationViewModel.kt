package com.mdelbel.android.pedidosya.presentation.navigation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mdelbel.android.pedidosya.domain.Point
import com.mdelbel.android.pedidosya.gateway.RequestState
import com.mdelbel.android.pedidosya.gateway.authentication.AuthenticationGateway

class NavigationViewModel : ViewModel() {

    private val _navigation = MutableLiveData<Navigation>()
    val navigation: LiveData<Navigation> get() = _navigation

    fun navigateTo(navigation: Navigation) {
        _navigation.postValue(navigation)
    }
}