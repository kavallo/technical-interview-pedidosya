package com.mdelbel.android.pedidosya.presentation.splash

import com.mdelbel.android.pedidosya.presentation.splash.ConditionStatus.*

data class GeneralCondition(private val token: ConditionStatus, private val location: ConditionStatus) {

    fun areConditionsMet() = token == READY && (location == READY || location == FAILED) // TODO mas objetoso
}

enum class ConditionStatus { READY, WAITING, FAILED }