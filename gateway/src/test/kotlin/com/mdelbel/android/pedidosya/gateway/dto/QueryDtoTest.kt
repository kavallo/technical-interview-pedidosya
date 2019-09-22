package com.mdelbel.android.pedidosya.gateway.dto

import com.google.common.truth.Truth.assertThat
import com.mdelbel.android.pedidosya.domain.Country
import com.mdelbel.android.pedidosya.domain.Point
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import org.junit.Test

class QueryDtoTest {

    @Test
    fun `pointAsString should delegate it to point`() {
        val point = mock<Point> { on { asString() } doReturn "-34.9033,-56.1882" }

        val query = QueryDto(point, mock())

        assertThat(query.pointAsString()).isEqualTo("-34.9033,-56.1882")
    }

    @Test
    fun `countryId should delegate it to country`() {
        val country = mock<Country> { on { id } doReturn 1 }

        val query = QueryDto(mock(), country)

        assertThat(query.countryId()).isEqualTo(1)
    }
}