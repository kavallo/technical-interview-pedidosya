package com.mdelbel.android.pedidosya.presentation.list

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mdelbel.android.pedidosya.presentation.R

internal class MarginItemDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        rect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val resources = view.context.resources

        rect.top = resources.getDimensionPixelOffset(R.dimen.item_restaurant_margin_top)
        rect.left = resources.getDimensionPixelOffset(R.dimen.item_restaurant_margin_left)
    }
}