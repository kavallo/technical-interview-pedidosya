package com.mdelbel.android.pedidosya.presentation.restaurants.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.mdelbel.android.pedidosya.domain.Restaurant
import com.mdelbel.android.pedidosya.presentation.R
import org.koin.android.viewmodel.ext.android.viewModel

class RestaurantsOnMapScreen : Fragment() {

    companion object {
        private const val MAP_ZOOM = 15f
    }

    private val restaurantsViewModel: RestaurantsOnMapViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.screen_restaurants_on_map, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpMapWhenIsReady()
    }

    private fun setUpMapWhenIsReady() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.restaurants_map)
        mapFragment as SupportMapFragment
        mapFragment.getMapAsync { googleMap ->
            restaurantsViewModel.fetchRestaurantsFromCache()

            setUpMarkerSelectionListener(googleMap)

            observeHighlightedRestaurant(googleMap)
            observeRestaurants(googleMap)
        }
    }

    private fun setUpMarkerSelectionListener(map: GoogleMap) {
        map.setOnMarkerClickListener {
            val restaurant = it.tag as Restaurant
            restaurantsViewModel.selected.value = restaurant
            it.showInfoWindow()
            true
        }
    }

    private fun observeHighlightedRestaurant(map: GoogleMap) {
        restaurantsViewModel.selected.observe(this, Observer {
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(it.latLng(), MAP_ZOOM))
        })
    }

    private fun observeRestaurants(map: GoogleMap) {
        restaurantsViewModel.restaurants.observe(this, Observer {
            it.forEach { restaurant ->
                restaurant.latLng()
                val markerOption = MarkerOptions()
                    .position(restaurant.latLng())
                    .title(restaurant.name)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_location))

                with(map.addMarker(markerOption)) { tag = restaurant }
            }
        })
    }
}

private fun Restaurant.latLng(): LatLng = LatLng(coordinates.latitude, coordinates.longitude)