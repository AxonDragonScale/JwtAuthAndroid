package com.axondragonscale.jwtauth.auth

/**
 * Created by Ronak Harkhani on 28/05/22
 */
sealed class AuthResult<T>(data: T? = null) {
    class Authorized<T>(data: T? = null): AuthResult<T>(data)
    class Unauthorized<T>: AuthResult<T>()
    class UknownError<T>: AuthResult<T>()
}