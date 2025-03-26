package com.freddy.proyectoqrasistencia.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://192.168.1.100:5000/" // Cambia por la IP donde corre tu API Flask

    val apiService: InvitadoApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(InvitadoApiService::class.java)
    }
}