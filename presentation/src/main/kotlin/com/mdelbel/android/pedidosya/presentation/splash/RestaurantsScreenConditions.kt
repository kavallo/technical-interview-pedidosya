package com.mdelbel.android.pedidosya.presentation.splash

import com.mdelbel.android.pedidosya.presentation.splash.ConditionStatus.*

internal data class RestaurantsScreenConditions(
    private val authentication: AuthenticationCondition,
    private val geolocation: GeolocationCondition
) {

    fun executeIfConditionsAreMet(block: () -> Unit) {
        if (authentication.isConditionMet() && geolocation.isConditionMet()) block()
    }
}

internal sealed class Condition(protected val status: ConditionStatus) {

    abstract fun isConditionMet(): Boolean
}

internal class AuthenticationCondition(status: ConditionStatus) : Condition(status) {

    override fun isConditionMet() = status == READY
}

internal class GeolocationCondition(status: ConditionStatus) : Condition(status) {

    override fun isConditionMet() = status == READY || status == FAILED
}

internal enum class ConditionStatus { READY, WAITING, FAILED }