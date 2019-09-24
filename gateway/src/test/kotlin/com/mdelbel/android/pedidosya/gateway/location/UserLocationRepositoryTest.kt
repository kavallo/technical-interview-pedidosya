package com.mdelbel.android.pedidosya.gateway.location

import android.content.SharedPreferences
import com.google.common.truth.Truth.assertThat
import com.mdelbel.android.pedidosya.domain.Point
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test

class UserLocationRepositoryTest {

    @Test
    fun `save should save user location on preference`() {
        val preferencesEditor = mock<SharedPreferences.Editor>()
        val preferences = mock<SharedPreferences> { on { edit() } doReturn preferencesEditor }
        val point = mock<Point> { on { asString() } doReturn "-34.8895,-56.1907" }
        val repository = UserLocationRepository(preferences)

        repository.save(point)

        verify(preferences).edit()
        verify(preferencesEditor).putString("USER_POINT", "-34.8895,-56.1907")
    }

    @Test
    fun `obtainLastKnown with unknown location should return default`() {
        val preferences = mock<SharedPreferences> {
            on { getString("USER_POINT", "") } doReturn ""
        }
        val default = mock<Point>()
        val repository = UserLocationRepository(preferences)

        val result = repository.obtainLastKnown(default)

        assertThat(result).isEqualTo(default)
    }

    @Test
    fun `obtainLastKnown with known location should return it`() {
        val expected = Point(latitude = -34.8895, longitude = -56.1907)
        val preferences = mock<SharedPreferences> {
            on { getString("USER_POINT", "") } doReturn "-34.8895,-56.1907"
        }
        val repository = UserLocationRepository(preferences)

        val result = repository.obtainLastKnown(mock())

        assertThat(result).isEqualTo(expected)
    }

}