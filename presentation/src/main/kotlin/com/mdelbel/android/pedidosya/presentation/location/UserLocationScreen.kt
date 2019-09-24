package com.mdelbel.android.pedidosya.presentation.location

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
import com.mdelbel.android.pedidosya.domain.Point
import com.mdelbel.android.pedidosya.presentation.R
import kotlinx.android.synthetic.main.view_location.*
import org.koin.android.viewmodel.ext.android.viewModel

class UserLocationScreen : Fragment() {

    companion object {
        private const val MAP_ZOOM = 15f
    }

    private val userLocationViewModel: UserLocationViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.screen_user_location, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpMapWhenIsReady()
    }

    private fun setUpMapWhenIsReady() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.use_location_map) as SupportMapFragment
        mapFragment.getMapAsync { googleMap ->
            setUpMap(googleMap)
            observeLocationUpdates(googleMap)
        }
    }

    private fun setUpMap(map: GoogleMap) {
        map.setOnMapClickListener { userLocationViewModel.update(Point(it.latitude, it.longitude)) }

        val resources = context!!.resources
        val paddingBottom = resources.getDimensionPixelOffset(R.dimen.user_location_map_padding_bottom)
        val paddingHorizontal = resources.getDimensionPixelOffset(R.dimen.user_location_map_padding_horizontal)
        map.setPadding(paddingHorizontal, 0, paddingHorizontal, paddingBottom)
    }

    private fun observeLocationUpdates(map: GoogleMap) {
        userLocationViewModel.location.observe(this, Observer { point ->
            selectPointOnMap(point, map)
            selectPointOnCard(point)
        })
    }

    private fun selectPointOnCard(point: Point) {
        location_description.text = point.asString()
        location_action.setOnClickListener { userLocationViewModel.save() }
    }

    private fun selectPointOnMap(point: Point, map: GoogleMap) {
        map.clear()

        val markerOption = MarkerOptions()
            .position(LatLng(point.latitude, point.longitude))
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_location))

        with(map.addMarker(markerOption)) {
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(position, MAP_ZOOM))
        }
    }
}