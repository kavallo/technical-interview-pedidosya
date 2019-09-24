package com.mdelbel.android.pedidosya.presentation.list

import android.content.Context
import android.content.res.Resources
import android.graphics.Rect
import android.view.View
import com.google.common.truth.Truth.assertThat
import com.mdelbel.android.pedidosya.presentation.R
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import org.junit.Test

class MarginItemDecorationTest {

    @Test
    fun `getItemOffsets should apply margin to items`() {
        val mockedResources = mock<Resources> {
            on { getDimensionPixelOffset(R.dimen.item_restaurant_margin_top) } doReturn 10
            on { getDimensionPixelOffset(R.dimen.item_restaurant_margin_left) } doReturn 12
        }
        val mockedContext = mock<Context> { on { resources } doReturn mockedResources }
        val itemDecoration = MarginItemDecoration()
        val rect = Rect()
        val view = mock<View> { on { context } doReturn mockedContext }

        itemDecoration.getItemOffsets(rect, view, mock(), mock())

        assertThat(rect.top).isEqualTo(10)
        assertThat(rect.left).isEqualTo(12)
    }
}