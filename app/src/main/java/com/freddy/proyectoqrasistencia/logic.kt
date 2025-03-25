package com.freddy.proyectoqrasistencia

/*fun main() {
    println("¡Hola, mundo!")
}*/


import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

class AuthManager(private val client: HttpClient) {
    private var currentToken: String? = null
    private var userRole: String? = null

    suspend fun login(username: String, password: String): Boolean {
        return try {
            val requestBody = """{"username":"$username","password":"$password"}"""

            val response: HttpResponse = client.post("https://tu-api.com/auth") {
                contentType(ContentType.Application.Json)
                setBody(requestBody)
            }

            if (response.status == HttpStatusCode.OK) {
                val responseBody = response.body<String>()
                val jsonResponse = Json.parseToJsonElement(responseBody).jsonObject
                currentToken = jsonResponse["token"]?.jsonPrimitive?.content
                userRole = jsonResponse["role"]?.jsonPrimitive?.content
                true
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }

    fun getUserRole(): String? {
        return userRole
    }
}
