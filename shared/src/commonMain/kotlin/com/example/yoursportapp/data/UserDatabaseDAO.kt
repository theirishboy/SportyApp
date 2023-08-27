package com.example.yoursportapp.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpTimeout
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

object HttpClientProvider {
    val httpClient = HttpClient {
        install(HttpTimeout) {
            requestTimeoutMillis = 10000

        }
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }

    }
}
class UserDatabaseDAO(private val httpClient: HttpClient = HttpClientProvider.httpClient)   {
    private var token: UserToken = UserToken(null,null)
    private val ApiURL = "http://10.0.2.2:8000"

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
                httpClient.post(ApiURL + ApiRoutes.TOKEN){
                    headers {
                        append(HttpHeaders.Accept, "application/json")
                        append(HttpHeaders.ContentType, "application/x-www-form-urlencoded")
                    }
                    val requestBody = "grant_type=&username=$username&password=$password&scope=&client_id=&client_secret="
                    setBody(requestBody)


                }.bodyAsText()
            if (response.contains("token"))
            {
                token = Json.decodeFromString(response)
            }
        }   catch (e: IOException) {
            println("Caught IOException: $e")
            response = "NetworkException"
        }  catch(e : Exception){
            response = "Can't connect to server, it might be down look at : $e"
        }
        println(response)
        return response

    }
    @Throws(Exception::class)
    suspend fun getSportSession(): String {

        val response =
            httpClient.get(ApiURL + ApiRoutes.SPORT_SESSION + "/1"){
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