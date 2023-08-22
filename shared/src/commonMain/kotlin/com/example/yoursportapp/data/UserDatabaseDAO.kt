package com.example.yoursportapp.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.errors.IOException
import kotlinx.serialization.json.Json

class UserDatabaseDAO {
    private var token: UserToken = UserToken(null,null)
    private val ApiURL = "http://10.0.2.2:8000/"
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }
    @Throws(Exception::class)
        suspend fun isServerUp(): String {
        val response =
            httpClient.get(ApiURL).body<String>()
        return response

    }
    @Throws(Exception::class)
    suspend fun signIn(username : String, password : String): String {
        var response = ""
        try {
            response =
                httpClient.post(ApiURL + "token"){
                    headers {
                        append(HttpHeaders.Accept, "application/json")
                        append(HttpHeaders.ContentType, "application/x-www-form-urlencoded")
                    }
                    val requestBody = "grant_type=&username=$username&password=$password&scope=&client_id=&client_secret="
                    setBody(requestBody)


                }.bodyAsText()
            token = Json.decodeFromString<UserToken>(response)
        }   catch (e: IOException) {
            println("Caught IOException: $e")
            response = "NetworkException"
        }
        return response

    }
    @Throws(Exception::class)
    suspend fun getSportSession(): String {

        val response =
            httpClient.get(ApiURL + "sport-sessions/1"){
                headers {
                    append(HttpHeaders.Accept, "application/json")
                    append(HttpHeaders.Authorization, ("bearer " + token.token))
                }


            }.bodyAsText()
        val json = Json { ignoreUnknownKeys = true }
        var sportSession = json.decodeFromString<SportSession>(response)
        return response
    }
}