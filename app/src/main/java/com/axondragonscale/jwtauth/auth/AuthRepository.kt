package com.axondragonscale.jwtauth.auth

import android.content.SharedPreferences
import com.axondragonscale.jwtauth.data.model.AuthRequest
import retrofit2.HttpException

/**
 * Created by Ronak Harkhani on 28/05/22
 */
class AuthRepository(
    private val api: AuthApi,
    private val prefs: SharedPreferences
): IAuthRepository {
    override suspend fun signUp(username: String, password: String): AuthResult<Unit> {
        return try {
            api.signUp(AuthRequest(username, password))
            signIn(username, password)
        } catch (e: HttpException) {
            if (e.code() == 401) {
                AuthResult.Unauthorized()
            } else {
                AuthResult.UknownError()
            }
        } catch (e: Exception) {
            AuthResult.UknownError()
        }
    }

    override suspend fun signIn(username: String, password: String): AuthResult<Unit> {
        return try {
            val response = api.signIn(AuthRequest(username, password))
            prefs.edit().putString("jwt", response.authToken).apply()
            AuthResult.Authorized()
        } catch (e: HttpException) {
            if (e.code() == 401) {
                AuthResult.Unauthorized()
            } else {
                AuthResult.UknownError()
            }
        } catch (e: Exception) {
            AuthResult.UknownError()
        }
    }

    override suspend fun authenticate(): AuthResult<Unit> {
        return try {
            val token = prefs.getString("jwt", null) ?: return AuthResult.Unauthorized()
            api.authenticate("Bearer $token")
            AuthResult.Authorized()
        } catch (e: HttpException) {
            if (e.code() == 401) {
                AuthResult.Unauthorized()
            } else {
                AuthResult.UknownError()
            }
        } catch (e: Exception) {
            AuthResult.UknownError()
        }
    }
}