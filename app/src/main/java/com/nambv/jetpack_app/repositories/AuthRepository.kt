package com.nambv.jetpack_app.repositories

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor() {
    fun login(username: String, password: String): Boolean {
        return username == "user" && password == "password"
    }
}