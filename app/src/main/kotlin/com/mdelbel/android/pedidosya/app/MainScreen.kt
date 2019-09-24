package com.mdelbel.android.pedidosya.app

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import com.mdelbel.android.pedidosya.gateway.Failed
import com.mdelbel.android.pedidosya.gateway.Loaded
import com.mdelbel.android.pedidosya.gateway.Loading
import com.mdelbel.android.pedidosya.presentation.AuthenticationViewModel
import com.mdelbel.android.pedidosya.presentation.RestaurantsOnListScreen
import com.mdelbel.android.pedidosya.presentation.location.UserLocationScreen
import kotlinx.android.synthetic.main.screen_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainScreen : AppCompatActivity() {

    companion object {
        private const val TAG_RESTAURANTS_FRAGMENT = "TAG_RESTAURANTS_FRAGMENT"
    }

    private val authViewModel by viewModel<AuthenticationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_main)

        if (savedInstanceState == null) obtainAccessToken()
    }

    // TODO handle error and loading
    private fun obtainAccessToken() {
        authViewModel.obtainAccessToken().observe(this, Observer { state ->
            when (state) {
                is Loading -> Toast.makeText(this, "Loading", Toast.LENGTH_LONG).show()
                is Loaded -> showRestaurants()
                is Failed -> Toast.makeText(this, "Failed", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun showRestaurants() = supportFragmentManager.commit {
        replace(main_container.id, UserLocationScreen(), TAG_RESTAURANTS_FRAGMENT)
    }
}