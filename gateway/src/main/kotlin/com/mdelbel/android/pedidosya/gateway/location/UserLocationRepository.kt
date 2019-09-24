package com.mdelbel.android.pedidosya.gateway.location

import android.content.SharedPreferences
import androidx.core.content.edit
import com.mdelbel.android.pedidosya.domain.Point

class UserLocationRepository internal constructor(private val preferences: SharedPreferences) {

    companion object {
        private const val USER_POINT = "USER_POINT"
    }

    fun save(userLocation: Point) {
        preferences.edit { putString(USER_POINT, userLocation.asString()) }
    }

    fun obtainLastKnown(default: Point): Point {
        val userLocation = preferences.getString(USER_POINT, "")

        if (userLocation.isNullOrBlank()) return default

        val coordinates = userLocation.split(",")
        return Point(latitude = coordinates[0].toDouble(), longitude = coordinates[1].toDouble())
    }
}