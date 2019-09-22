package com.mdelbel.android.pedidosya.gateway

import androidx.lifecycle.LiveData
import androidx.paging.PagedList

data class PagedListing<T>(
    val pagedList: LiveData<PagedList<T>>,
    val requestState: LiveData<RequestState>
)