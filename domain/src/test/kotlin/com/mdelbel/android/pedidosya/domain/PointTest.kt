package com.mdelbel.android.pedidosya.domain

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class PointTest {

    @Test
    fun `assert Montevideo coordinates`() {
        val expectedPointForMontevideo = Point(latitude = -34.9033, longitude = -56.1882)
        assertThat(Point.Montevideo).isEqualTo(expectedPointForMontevideo)
    }

    @Test
    fun `asString should return latitude and longitude as string separated with a comma`() {
        val point = Point(latitude = -34.9033, longitude = -56.1882)

        assertThat(point.asString()).isEqualTo("-34.9033,-56.1882")
    }
}