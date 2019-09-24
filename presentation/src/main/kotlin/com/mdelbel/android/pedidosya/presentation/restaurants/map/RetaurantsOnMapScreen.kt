package com.mdelbel.android.pedidosya.presentation.restaurants.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.mdelbel.android.pedidosya.domain.Point
import com.mdelbel.android.pedidosya.presentation.R
import kotlinx.android.synthetic.main.view_location.*
import org.koin.android.viewmodel.ext.android.viewModel

class RetaurantsOnMapScreen : Fragment() {

    companion object {
        private const val MAP_ZOOM = 15f
    }

    private val userLocationViewModel: RestaurantsOnMapViewModel by viewModel()

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
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.use_location_map) as SupportMapFragment
        mapFragment.getMapAsync { googleMap ->
            setUpMap(googleMap)
            showRestaurants(googleMap)
        }
    }

    private fun setUpMap(map: GoogleMap) {
        val resources = context!!.resources
        val paddingBottom =
            resources.getDimensionPixelOffset(R.dimen.user_location_map_padding_bottom)
        val paddingHorizontal =
            resources.getDimensionPixelOffset(R.dimen.user_location_map_padding_horizontal)
        map.setPadding(paddingHorizontal, 0, paddingHorizontal, paddingBottom)
    }

    private fun showRestaurants(map: GoogleMap) {
        val restaurants = userLocationViewModel.obtainAll()
        restaurants.forEach {
            val markerOption = MarkerOptions()
                .position(LatLng(it.coordinates.latitude, it.coordinates.longitude))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_location))

            map.addMarker(markerOption)
        }
    }
}