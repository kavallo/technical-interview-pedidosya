package com.mdelbel.android.pedidosya.presentation.splash

import com.mdelbel.android.pedidosya.presentation.splash.ConditionStatus.*
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ConditionTest {

    @Test
    fun `AuthenticationCondition isConditionMet with ready should return true`() {
        val condition = AuthenticationCondition(READY)

        assertTrue(condition.isConditionMet())
    }

    @Test
    fun `AuthenticationCondition isConditionMet with waiting should return false`() {
        val condition = AuthenticationCondition(WAITING)

        assertFalse(condition.isConditionMet())
    }

    @Test
    fun `AuthenticationCondition isConditionMet with failed should return false`() {
        val condition = AuthenticationCondition(FAILED)

        assertFalse(condition.isConditionMet())
    }

    @Test
    fun `GeolocationCondition isConditionMet with ready should return true`() {
        val condition = GeolocationCondition(READY)

        assertTrue(condition.isConditionMet())
    }

    @Test
    fun `GeolocationCondition isConditionMet with waiting should return false`() {
        val condition = GeolocationCondition(WAITING)

        assertFalse(condition.isConditionMet())
    }

    @Test
    fun `GeolocationCondition isConditionMet with failed should return true`() {
        val condition = GeolocationCondition(FAILED)

        assertTrue(condition.isConditionMet())
    }
}