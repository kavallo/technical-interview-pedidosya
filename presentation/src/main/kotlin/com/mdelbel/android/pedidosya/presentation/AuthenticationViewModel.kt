package com.mdelbel.android.pedidosya.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mdelbel.android.pedidosya.gateway.RequestState
import com.mdelbel.android.pedidosya.gateway.authentication.AuthenticationGateway

class AuthenticationViewModel
    (private val authenticationGateway: AuthenticationGateway) : ViewModel() {

    fun obtainAccessToken(): LiveData<RequestState> = authenticationGateway.authenticate()
}