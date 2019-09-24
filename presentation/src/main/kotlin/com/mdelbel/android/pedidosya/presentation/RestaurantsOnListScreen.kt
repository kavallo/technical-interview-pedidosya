package com.mdelbel.android.pedidosya.presentation

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.mdelbel.android.pedidosya.presentation.list.MarginItemDecoration
import com.mdelbel.android.pedidosya.presentation.list.RestaurantsAdapter
import kotlinx.android.synthetic.main.screen_restaurants_on_list.*
import org.koin.android.viewmodel.ext.android.viewModel
import androidx.appcompat.app.AppCompatActivity
import com.mdelbel.android.pedidosya.presentation.navigation.NavigationViewModel
import com.mdelbel.android.pedidosya.presentation.navigation.UserLocationNavigation
import org.koin.android.viewmodel.ext.android.sharedViewModel

class RestaurantsOnListScreen : Fragment() {

    private val restaurantsViewModel: RestaurantsViewModel by viewModel()
    private val navigationViewModel: NavigationViewModel by sharedViewModel()

    private val restaurantsAdapter = RestaurantsAdapter()

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
        setUpList()
        setHasOptionsMenu(true)

        val activity = activity as AppCompatActivity?
        activity!!.setSupportActionBar(toolbarView)
        activity.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbarView.setOnMenuItemClickListener {
            navigationViewModel.navigateTo(UserLocationNavigation)
            true
        }

        observeRestaurants()
        observeRequestState()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_restaurants, menu)
    }

    private fun setUpList() {
        restaurants.addItemDecoration(MarginItemDecoration())
        restaurants.adapter = restaurantsAdapter
    }

    private fun observeRestaurants() {
        restaurantsViewModel.pages
            .observe(this, Observer { movies -> restaurantsAdapter.submitList(movies) })
    }

    private fun observeRequestState() {
        restaurantsViewModel.state
            .observe(this, Observer { state -> restaurantsAdapter.onStateChanges(state) })
    }
}