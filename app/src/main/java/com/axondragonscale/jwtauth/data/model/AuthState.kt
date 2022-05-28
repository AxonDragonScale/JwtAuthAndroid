package com.axondragonscale.jwtauth.data.model

/**
 * Created by Ronak Harkhani on 28/05/22
 */
data class AuthState(
    val isLoading: Boolean = false,
    val signUpUsername: String = "",
    val signUpPassword: String = "",
    val signInUsername: String = "",
    val signInPassword: String = ""
)