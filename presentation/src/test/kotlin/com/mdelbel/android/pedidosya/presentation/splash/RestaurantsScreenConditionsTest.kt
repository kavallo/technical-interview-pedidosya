package com.mdelbel.android.pedidosya.presentation.splash

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Test

class RestaurantsScreenConditionsTest {

    @Test
    fun `executeIfConditionsAreMet with auth and geo conditions not met should not execute it`() {
        val authenticationCondition = mock<AuthenticationCondition> {
            on { isConditionMet() } doReturn false
        }
        val geolocationCondition = mock<GeolocationCondition> {
            on { isConditionMet() } doReturn false
        }
        val conditions = RestaurantsScreenConditions(authenticationCondition, geolocationCondition)

        conditions.executeIfConditionsAreMet {
            fail()
        }
    }

    @Test
    fun `executeIfConditionsAreMet with auth condition not met should not execute it`() {
        val authenticationCondition = mock<AuthenticationCondition> {
            on { isConditionMet() } doReturn false
        }
        val geolocationCondition = mock<GeolocationCondition> {
            on { isConditionMet() } doReturn true
        }
        val conditions = RestaurantsScreenConditions(authenticationCondition, geolocationCondition)

        conditions.executeIfConditionsAreMet {
            fail()
        }
    }

    @Test
    fun `executeIfConditionsAreMet with geo condition not met should not execute it`() {
        val authenticationCondition = mock<AuthenticationCondition> {
            on { isConditionMet() } doReturn true
        }
        val geolocationCondition = mock<GeolocationCondition> {
            on { isConditionMet() } doReturn false
        }
        val conditions = RestaurantsScreenConditions(authenticationCondition, geolocationCondition)

        conditions.executeIfConditionsAreMet {
            fail()
        }
    }

    @Test
    fun `executeIfConditionsAreMet with auth and geo condition met should not execute it`() {
        val authenticationCondition = mock<AuthenticationCondition> {
            on { isConditionMet() } doReturn true
        }
        val geolocationCondition = mock<GeolocationCondition> {
            on { isConditionMet() } doReturn true
        }
        val conditions = RestaurantsScreenConditions(authenticationCondition, geolocationCondition)

        conditions.executeIfConditionsAreMet {
            assertTrue(true)
        }
    }
}