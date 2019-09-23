package com.mdelbel.android.pedidosya.gateway.authentication

/**
 * Could be improved to save the token on a preferences or in a DB.
 */
internal data class Keychain(var accessToken: AccessToken? = null)