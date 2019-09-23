package com.mdelbel.android.pedidosya.gateway.authentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mdelbel.android.pedidosya.gateway.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthenticationGateway internal constructor(
    private val service: AuthenticationService,
    private val keychain: Keychain
) {

    fun authenticate(): LiveData<RequestState> {
        val state = MutableLiveData<RequestState>()
        state.postValue(Loading)

        val authenticationCall = obtainAccessTokenCallFromService()
        authenticationCall.enqueue(object : Callback<AccessToken> {

            override fun onResponse(call: Call<AccessToken>, response: Response<AccessToken>) {
                keychain.accessToken = response.body()
                state.postValue(Loaded)
            }

            override fun onFailure(call: Call<AccessToken>, t: Throwable) =
                state.postValue(Failed(AuthenticationException(t)))
        })

        return state
    }

    private fun obtainAccessTokenCallFromService() = service.obtainAccessToken(
        clientId = BuildConfig.PEDIDOS_YA_CLIENT_ID,
        clientSecret = BuildConfig.PEDIDOS_YA_CLIENT_SECRET
    )
}

class AuthenticationException(cause: Throwable) : RuntimeException(cause)