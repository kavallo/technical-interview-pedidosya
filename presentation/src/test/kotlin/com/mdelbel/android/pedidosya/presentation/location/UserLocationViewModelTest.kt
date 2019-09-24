package com.mdelbel.android.pedidosya.presentation.location

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.mdelbel.android.pedidosya.domain.Point
import com.mdelbel.android.pedidosya.gateway.location.UserLocationRepository
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Rule
import org.junit.Test

class UserLocationViewModelTest {

    @get:Rule
    var testRule = InstantTaskExecutorRule()

    @Test
    fun `viewModel creation should post last known location`() {
        val expected = mock<Point>()
        val userLocationRepository = mock<UserLocationRepository> {
            on { obtainLastKnown(any()) } doReturn expected
        }
        val viewModel = UserLocationViewModel(userLocationRepository)

        val point = viewModel.location.value

        assertThat(point).isEqualTo(expected)
    }

    @Test
    fun `viewModel creation should pass Montevideo as default`() {
        val userLocationRepository = mock<UserLocationRepository>()
        UserLocationViewModel(userLocationRepository)

        verify(userLocationRepository).obtainLastKnown(Point.Montevideo)
    }

    @Test
    fun `update should notify value`() {
        val expected = mock<Point>()
        val userLocationRepository = mock<UserLocationRepository>()
        val viewModel = UserLocationViewModel(userLocationRepository)

        viewModel.update(expected)

        assertThat(viewModel.location.value).isEqualTo(expected)
    }

    @Test
    fun `save should update value on repository`() {
        val expected = mock<Point>()
        val userLocationRepository = mock<UserLocationRepository>()
        val viewModel = UserLocationViewModel(userLocationRepository)

        viewModel.save(expected)

        verify(userLocationRepository).save(expected)
    }
}