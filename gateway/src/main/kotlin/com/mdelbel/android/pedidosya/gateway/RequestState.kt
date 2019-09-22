package com.mdelbel.android.pedidosya.gateway

sealed class RequestState

object Loading : RequestState()

object Loaded : RequestState()

class Failed(val cause: Exception) : RequestState()