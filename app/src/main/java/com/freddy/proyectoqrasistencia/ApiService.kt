package com.freddy.proyectoqrasistencia

import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("users")
    suspend fun getUsers(@Path("id") userId: Int): User
}