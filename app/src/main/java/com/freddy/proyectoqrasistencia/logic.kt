package com.freddy.proyectoqrasistencia


import io.ktor.client.*
import io.ktor.client.engine.cio.CIO
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException

fun main() {

    AuthManager.login("41076086", "secret18")

    println("Hello, World!")
}


class AuthManager {
    companion object {

        private val client = HttpClient()

        fun login(dni: String, password: String): Boolean = runBlocking {
            try {
                val requestBody = """{"dni":"$dni","password":"$password"}"""
                val response = client.post("http://127.0.0.1:5000/login") {
                    contentType(ContentType.Application.Json)
                    setBody(requestBody)
                }

                if (response.status == HttpStatusCode.OK) {
                    val responseBody = response.bodyAsText()
                    println(responseBody)
                    val jsonResponse = Json.parseToJsonElement(responseBody).jsonObject
                    val token = jsonResponse["access_token"]?.jsonPrimitive?.content
                    println(token)
                    val decodedJWT = JWT.decode(token)

                    // Store token and role if needed
                    // You might want to add static storage or use a separate instance
                    true
                } else {
                    false
                }
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }
}
