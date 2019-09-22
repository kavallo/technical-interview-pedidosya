package com.mdelbel.android.pedidosya.domain

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class CountryTest {

    @Test
    fun `Uruguay id should be 1`() {
        assertThat(Uruguay.id).isEqualTo(1)
    }
}