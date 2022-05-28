package com.axondragonscale.jwtauth.auth

import com.axondragonscale.jwtauth.data.model.AuthRequest
import com.axondragonscale.jwtauth.data.model.TokenResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

/**
 * Created by Ronak Harkhani on 28/05/22
 */
interface AuthApi {

    @POST("signUp")
    suspend fun signUp(@Body authRequest: AuthRequest)

    @POST("signIn")
    suspend fun signIn(@Body authRequest: AuthRequest): TokenResponse

    @GET("authenticate")
    suspend fun authenticate(@Header("Authorization") token: String)
}