package com.freddy.proyectoqrasistencia


import io.ktor.client.*
import io.ktor.client.engine.cio.CIO
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.*
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
import com.freddy.proyectoqrasistencia.AuthManager.Companion.getAuthenticatedClient
//import android.util.Base64
import java.util.Base64
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun main() {

    AuthManager.login("41076086", "secret18")
    runBlocking {

        //val httpResponse = AuthManager.getAuthenticatedClient().get("http://apisenatiamarillo.onrender.com/vigilantes")
        val httpResponse = AuthManager.getAuthenticatedClient().get("http://127.0.0.1:5000/vigilantes")
        println(httpResponse.bodyAsText())
        val map = AuthManager.getJWTData()
        println(map?.get("role"))

    }

    println("Hello, World!")


}

fun decodeJwtPayload(jwt: String): String {
    // Split the JWT by dots
    val parts = jwt.split(".")

    // Make sure we have at least 2 parts
    if (parts.size < 2) {
        throw IllegalArgumentException("Invalid JWT format")
    }

    // Get the middle part (payload)
    val encodedPayload = parts[1]

    // Use the URL decoder directly - it handles the lack of padding
    val decodedBytes = Base64.getUrlDecoder().decode(encodedPayload)

    // Convert bytes to string
    return String(decodedBytes, Charsets.UTF_8)
}


/*fun decodeJwtPayload(jwt: String): String {
    // Split the JWT by dots
    val parts = jwt.split(".")

    // Make sure we have at least 3 parts
    if (parts.size < 2) {
        throw IllegalArgumentException("Invalid JWT format")
    }

    // Get the middle part (payload)
    val encodedPayload = parts[1]

    // For Android
    val decodedBytes = Base64.decode(encodedPayload, Base64.URL_SAFE)

    // For Java/Kotlin (non-Android)
    // val decodedBytes = java.util.Base64.getUrlDecoder().decode(encodedPayload)

    // Convert bytes to string
    return String(decodedBytes, Charsets.UTF_8)
}*/

interface DataStorage {
    fun saveString(key: String, value: String)
    fun getString(key: String): String?
}

class InMemoryStorage : DataStorage {
    private val storage = mutableMapOf<String, String>()

    override fun saveString(key: String, value: String) {
        storage[key] = value
    }

    override fun getString(key: String): String? {
        return storage[key]
    }
}

class TokenManager {
    private var currentToken: String? = null

    fun setToken(token: String) {
        currentToken = token
    }

    fun getHttpClient(): HttpClient {
        return HttpClient() {
            // Automatically add token to every request
            defaultRequest {
                currentToken?.let {
                    header(HttpHeaders.Authorization, "Bearer $it")
                }
            }
        }
    }
}

class AuthManager {
    companion object {

        private val client = HttpClient()
        public val storage: DataStorage = InMemoryStorage()
        private val tokenManager = TokenManager()


        fun getJWTData(): Map<String, Any>? {

            val jsonString = storage.getString("decoded")
            if (jsonString == null) {
                return null
            }

            val gson = Gson()
            val mapType = object : TypeToken<Map<String, Any>>() {}.type
            val map: Map<String, Any> = gson.fromJson(jsonString, mapType)

            return map

        }

        fun getJWT(): String? {

            return storage.getString("token")

        }

        fun getAuthenticatedClient() = tokenManager.getHttpClient()

        fun login(dni: String, password: String): Boolean = runBlocking {
            try {
                val requestBody = """{"dni":"$dni","password":"$password"}"""
                //val response = client.post("http://apisenatiamarillo.onrender.com/login") {
                val response = client.post("http://127.0.0.1:5000/login") {
                    contentType(ContentType.Application.Json)
                    setBody(requestBody)

                }

                if (response.status == HttpStatusCode.OK) {
                    val responseBody = response.bodyAsText()
                    println(responseBody)
                    val jsonResponse = Json.parseToJsonElement(responseBody).jsonObject
                    val token = jsonResponse["token"]?.jsonPrimitive?.content
                    println(token)
                    val decoded = decodeJwtPayload(token!!)
                    println(decoded)
                    storage.saveString("token", token)
                    storage.saveString("decoded", decoded)
                    tokenManager.setToken(token)
                    //val httpResponse = getAuthenticatedClient().get("http://apisenatiamarillo.onrender.com/vigilantes")
                    //println(httpResponse.bodyAsText())







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
