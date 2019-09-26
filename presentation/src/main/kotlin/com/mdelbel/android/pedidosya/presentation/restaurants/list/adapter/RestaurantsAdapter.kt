package com.mdelbel.android.pedidosya.presentation.restaurants.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.mdelbel.android.pedidosya.domain.Restaurant
import com.mdelbel.android.pedidosya.presentation.R

internal class RestaurantsAdapter :
    PagedListAdapter<Restaurant, RestaurantViewHolder>(DIFF_CALLBACK) {

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Restaurant>() {

            override fun areItemsTheSame(old: Restaurant, new: Restaurant) = old.id == new.id

            override fun areContentsTheSame(old: Restaurant, new: Restaurant) = old == new
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_restaurant, parent, false)

        return RestaurantViewHolder(view)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        val restaurant = getItem(position)
        when (restaurant == null) {
            true -> holder.loading()
            false -> holder.bindTo(restaurant)
        }
    }
}