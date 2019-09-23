package com.mdelbel.android.pedidosya.presentation.list

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView
import com.mdelbel.android.pedidosya.domain.Restaurant
import com.mdelbel.android.pedidosya.presentation.R

class RestaurantViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val logo: SimpleDraweeView = itemView.findViewById(R.id.item_restaurant_logo)
    private val name: TextView = itemView.findViewById(R.id.item_restaurant_title)
    private val score: TextView = itemView.findViewById(R.id.item_restaurant_score)

    fun loading() {
        // TODO("not implemented")
    }

    fun bindTo(restaurant: Restaurant) {
        logo.setImageURI(restaurant.logoUrl)
        name.text = restaurant.name
        score.text = restaurant.score.toString()
    }
}