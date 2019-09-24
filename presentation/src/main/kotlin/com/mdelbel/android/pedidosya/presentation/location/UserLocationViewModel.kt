package com.mdelbel.android.pedidosya.presentation.location

import androidx.lifecycle.ViewModel
import com.mdelbel.android.pedidosya.domain.Point
import com.mdelbel.android.pedidosya.gateway.location.UserLocationRepository

class UserLocationViewModel(private val locationRepository: UserLocationRepository) : ViewModel() {

    fun obtainLastKnown(): Point =
        locationRepository.obtainLastKnown(default = Point.Montevideo)
}