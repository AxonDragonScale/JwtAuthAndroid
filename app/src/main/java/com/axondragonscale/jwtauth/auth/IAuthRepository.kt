package com.axondragonscale.jwtauth.auth

/**
 * Created by Ronak Harkhani on 28/05/22
 */
interface IAuthRepository {
    suspend fun signUp(username: String, password: String): AuthResult<Unit>
    suspend fun signIn(username: String, password: String): AuthResult<Unit>
    suspend fun authenticate(): AuthResult<Unit>
}