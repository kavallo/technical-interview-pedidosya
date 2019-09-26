package com.mdelbel.android.pedidosya.presentation.restaurants.list.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView
import com.mdelbel.android.pedidosya.domain.Restaurant
import com.mdelbel.android.pedidosya.presentation.R

class RestaurantViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val logo: SimpleDraweeView = itemView.findViewById(R.id.item_restaurant_logo)
    private val name: TextView = itemView.findViewById(R.id.item_restaurant_title)
    private val deliveryTime: TextView = itemView.findViewById(R.id.item_restaurant_delivery_time)
    private val score: TextView = itemView.findViewById(R.id.item_restaurant_score)
    private val paymentOnline: TextView = itemView.findViewById(R.id.item_restaurant_payment_online)

    fun bindTo(restaurant: Restaurant) {
        logo.setImageURI(restaurant.logoUrl)

        name.text = restaurant.name
        deliveryTime.text = restaurant.deliveryTime
        score.text = restaurant.score.toString()

        paymentOnline.text = when (restaurant.hasOnlinePaymentMethods) {
            true -> itemView.context.getString(R.string.restaurant_accept_online_payment)
            false -> itemView.context.getString(R.string.restaurant_cash_payment)
        }
    }
}