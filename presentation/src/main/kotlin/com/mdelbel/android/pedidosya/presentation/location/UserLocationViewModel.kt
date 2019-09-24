package com.mdelbel.android.pedidosya.presentation.location

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mdelbel.android.pedidosya.domain.Point
import com.mdelbel.android.pedidosya.gateway.location.UserLocationRepository

class UserLocationViewModel(private val userLocationRepository: UserLocationRepository) :
    ViewModel() {

    private val _location = MutableLiveData<Point>()
    internal val location: LiveData<Point> get() = _location

    init {
        val defaultLocation = userLocationRepository.obtainLastKnown(default = Point.Montevideo)
        _location.postValue(defaultLocation)
    }

    fun update(point: Point) {
        _location.postValue(point)
    }

    fun save(point: Point) {
        userLocationRepository.save(point)
    }
}