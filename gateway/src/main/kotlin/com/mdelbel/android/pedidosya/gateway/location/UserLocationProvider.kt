package com.mdelbel.android.pedidosya.gateway.location

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.FusedLocationProviderClient
import com.mdelbel.android.pedidosya.domain.Point
import com.mdelbel.android.pedidosya.gateway.Failed
import com.mdelbel.android.pedidosya.gateway.Loaded
import com.mdelbel.android.pedidosya.gateway.Loading
import com.mdelbel.android.pedidosya.gateway.RequestState

class UserLocationProvider internal constructor(
    private val source: FusedLocationProviderClient,
    private val repository: UserLocationRepository
) {

    @SuppressLint("MissingPermission")
    fun fetchLastKnown(): LiveData<RequestState> {
        val state = MutableLiveData<RequestState>()

        state.postValue(Loading)
        source.lastLocation
            .addOnSuccessListener {
                val point = Point(latitude = it.latitude, longitude = it.longitude)

                repository.save(point)
                state.postValue(Loaded)
            }.addOnFailureListener {
                state.postValue(Failed(it))
            }

        return state
    }
}