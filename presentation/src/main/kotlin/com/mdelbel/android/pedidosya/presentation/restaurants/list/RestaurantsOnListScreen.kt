package com.mdelbel.android.pedidosya.presentation.restaurants.list

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.mdelbel.android.pedidosya.domain.Restaurant
import com.mdelbel.android.pedidosya.gateway.Failed
import com.mdelbel.android.pedidosya.gateway.Loaded
import com.mdelbel.android.pedidosya.gateway.Loading
import com.mdelbel.android.pedidosya.gateway.PagedListing
import com.mdelbel.android.pedidosya.presentation.R
import com.mdelbel.android.pedidosya.presentation.restaurants.list.adapter.MarginItemDecoration
import com.mdelbel.android.pedidosya.presentation.restaurants.list.adapter.RestaurantsAdapter
import kotlinx.android.synthetic.main.screen_error.*
import kotlinx.android.synthetic.main.screen_restaurants_on_list.*
import org.koin.android.viewmodel.ext.android.viewModel

class RestaurantsOnListScreen : Fragment() {

    private val restaurantsViewModel by viewModel<RestaurantsOnListViewModel>()
    private lateinit var restaurantsAdapter: RestaurantsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.screen_restaurants_on_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpToolbar()
        setUpList()

        observeRestaurantsNearLastKnownLocation()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_restaurants, menu)
    }

    private fun setUpToolbar() {
        setHasOptionsMenu(true)
        val activity = activity as AppCompatActivity?
        activity!!.setSupportActionBar(toolbarView)

        toolbarView.setOnMenuItemClickListener {
            val action = when (it.itemId) {
                R.id.change_location -> R.id.action_restaurantsOnList_to_userLocation
                R.id.show_on_map -> R.id.action_restaurantsOnList_to_restaurantsOnMap
                else -> throw IllegalArgumentException("Cannot handle action ${it.itemId}")
            }
            findNavController().navigate(action)
            true
        }
    }

    private fun setUpList() {
        restaurants.addItemDecoration(MarginItemDecoration())

        restaurantsAdapter = RestaurantsAdapter()
        restaurants.adapter = restaurantsAdapter
    }

    private fun observeRestaurantsNearLastKnownLocation() {
        val pagedListing = restaurantsViewModel.fetchRestaurantsNearLastKnownLocation()

        observeRestaurants(pagedListing)
        observeRequestState(pagedListing)
    }

    private fun observeRestaurants(pagedListing: PagedListing<Restaurant>) {
        pagedListing.pagedList.observe(this, Observer { movies ->
            restaurantsAdapter.submitList(movies)
        })
    }

    private fun observeRequestState(pagedListing: PagedListing<Restaurant>) {
        pagedListing.requestState.observe(this, Observer { requestState ->
            when (requestState) {
                is Loading -> onLoading()
                is Loaded -> onLoaded(pagedListing)
                is Failed -> onFailed()
            }
        })
    }

    private fun onLoading() {
        loading.visibility = View.VISIBLE
    }

    private fun onLoaded(pagedListing: PagedListing<Restaurant>) {
        loading.visibility = View.GONE
        pagedListing.requestState.removeObservers(this)
    }

    private fun onFailed() {
        loading.visibility = View.GONE
        error_container.visibility = View.VISIBLE
    }
}