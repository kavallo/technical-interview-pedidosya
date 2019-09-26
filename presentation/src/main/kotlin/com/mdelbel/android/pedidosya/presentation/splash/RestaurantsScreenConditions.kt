package com.mdelbel.android.pedidosya.presentation.splash

import com.mdelbel.android.pedidosya.presentation.splash.ConditionStatus.FAILED
import com.mdelbel.android.pedidosya.presentation.splash.ConditionStatus.READY

internal data class RestaurantsScreenConditions(
    private val authentication: AuthenticationCondition,
    private val geolocation: GeolocationCondition
) {

    fun executeIfConditions(areMet: () -> Unit, onError: () -> Unit) {
        if (authentication.isConditionMet() && geolocation.isConditionMet()) areMet()
        if (authentication.hasFail() || geolocation.hasFail()) onError()
    }
}

internal sealed class Condition(protected val status: ConditionStatus) {

    abstract fun isConditionMet(): Boolean

    abstract fun hasFail(): Boolean
}

internal class AuthenticationCondition(status: ConditionStatus) : Condition(status) {

    override fun isConditionMet() = status == READY

    override fun hasFail() = status == FAILED
}

internal class GeolocationCondition(status: ConditionStatus) : Condition(status) {

    override fun isConditionMet() = status == READY || status == FAILED

    override fun hasFail() = false
}

internal enum class ConditionStatus { READY, WAITING, FAILED }