package com.mdelbel.android.pedidosya.gateway.restaurants

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.mdelbel.android.pedidosya.domain.Country
import com.mdelbel.android.pedidosya.domain.Point
import com.mdelbel.android.pedidosya.domain.Restaurant
import com.mdelbel.android.pedidosya.gateway.Failed
import com.mdelbel.android.pedidosya.gateway.Loaded
import com.mdelbel.android.pedidosya.gateway.Loading
import com.mdelbel.android.pedidosya.gateway.RequestState

internal class RestaurantDataSource(
    private val point: Point,
    private val country: Country,
    private val service: RestaurantsService,
    private val cache: RestaurantsCache
) : PageKeyedDataSource<Int, Restaurant>() {

    internal val requestState = MutableLiveData<RequestState>()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Restaurant>
    ) {
        fetch(page = 1, size = params.requestedLoadSize) { restaurants, next, total ->
            callback.onResult(restaurants, 0, total, null, next)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Restaurant>) {
        fetch(page = params.key, size = params.requestedLoadSize) { restaurants, next, _ ->
            callback.onResult(restaurants, next)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Restaurant>) {}

    private fun fetch(page: Int, size: Int, result: (List<Restaurant>, Int?, Int) -> Unit) {
        try {
            requestState.postValue(Loading)
            val (restaurants, next, total) = fetch(page = page, size = size)
            cache.obtainOn(point, country).addAll(restaurants)

            requestState.postValue(Loaded)
            result(restaurants, next, total)
        } catch (exception: Exception) {
            requestState.postValue(Failed(cause = exception))
        }
    }

    private fun fetch(page: Int, size: Int): Triple<List<Restaurant>, Int?, Int> {
        val restaurantsDto = service
            .fetchRestaurants(
                coordinate = point.asString(),
                countryId = country.id,
                page = page,
                pageSize = size
            )
            .execute()
            .body()

        val nextPage = restaurantsDto!!.nextPage(current = page, pageSize = size)

        return Triple(restaurantsDto.asModel(), nextPage, restaurantsDto.total)
    }
}