package com.freddy.proyectoqrasistencia

/*fun main() {
    println("¡Hola, mundo!")
}*/

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class AuthManager {
    private var currentToken: String? = null

    // Method 1: Using JsonObject
    suspend fun loginWithJsonObject(
        client: HttpClient,
        username: String,
        password: String
    ): Boolean {
        return try {
            val requestBody = buildJsonObject {
                put("username", username)
                put("password", password)
            }

            val response: HttpResponse = client.post("/login") {
                contentType(ContentType.Application.Json)
                setBody(requestBody)
            }

            if (response.status == HttpStatusCode.OK) {
                currentToken = response.body<String>() // Assuming direct token response
                true
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }

    // Method 2: Using String-based manual JSON
    suspend fun loginWithManualJson(
        client: HttpClient,
        username: String,
        password: String
    ): Boolean {
        return try {
            val response: HttpResponse = client.post("/login") {
                contentType(ContentType.Application.Json)
                setBody("""{"username":"$username","password":"$password"}""")
            }

            if (response.status == HttpStatusCode.OK) {
                currentToken = response.body<String>() // Assuming direct token response
                true
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }

    // Method 3: Using FormUrlEncoded
    suspend fun loginWithFormData(
        client: HttpClient,
        username: String,
        password: String
    ): Boolean {
        return try {
            val response: HttpResponse = client.post("/login") {
                contentType(ContentType.Application.FormUrlEncoded)
                body = Parameters.build {
                    append("username", username)
                    append("password", password)
                }
            }

            if (response.status == HttpStatusCode.OK) {
                currentToken = response.body<String>() // Assuming direct token response
                true
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }
}
