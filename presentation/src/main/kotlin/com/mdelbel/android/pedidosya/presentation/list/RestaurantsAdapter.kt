package com.mdelbel.android.pedidosya.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.mdelbel.android.pedidosya.domain.Restaurant
import com.mdelbel.android.pedidosya.gateway.Loaded
import com.mdelbel.android.pedidosya.gateway.Loading
import com.mdelbel.android.pedidosya.gateway.RequestState
import com.mdelbel.android.pedidosya.presentation.R

internal class RestaurantsAdapter :
    PagedListAdapter<Restaurant, RestaurantViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Restaurant>() {

            override fun areItemsTheSame(old: Restaurant, new: Restaurant) = old.id == new.id

            override fun areContentsTheSame(old: Restaurant, new: Restaurant) = old == new
        }
    }

    private var state: RequestState = Loaded

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_restaurant, parent, false)

        return RestaurantViewHolder(view)
    }

    // TODO place holder handling
    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        when (state is Loading) {
            true -> holder.loading()
            false -> holder.bindTo(restaurant = getItem(position)!!)
        }
    }

    override fun getItemCount(): Int {
        return when (state is Loading) {
            true -> super.getItemCount() + 1
            false -> super.getItemCount()
        }
    }

    fun onStateChanges(newState: RequestState) {
        val isLoadingVisible = state is Loading
        val shouldShowLoading = newState is Loading
        state = newState

        when {
            isLoadingVisible && !shouldShowLoading -> notifyItemRemoved(itemCount)
            shouldShowLoading && !isLoadingVisible -> notifyItemInserted(itemCount)
        }
    }
}