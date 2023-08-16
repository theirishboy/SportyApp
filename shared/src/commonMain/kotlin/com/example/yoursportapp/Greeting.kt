package com.example.yoursportapp
import com.example.yoursportapp.data.SportSession
import com.example.yoursportapp.data.UserToken
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class Greeting {
    private val platform: Platform = getPlatform()
    private var token: UserToken? = null
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }
    //    @Throws(Exception::class)
//    suspend fun greet(): String {
//        val rockets: List<RocketLaunch> =
//            httpClient.get("https://api.spacexdata.com/v4/launches").body()
//        val lastSuccessLaunch = rockets.last { it.launchSuccess == true }
//        return "Guess what it is! > ${platform.name.reversed()}!" +
//                "\nThere are only ${daysUntilNewYear()} left until New Year! 🎆" +
//                "\nThe last successful launch was ${lastSuccessLaunch.launchDateUTC} 🚀"
//
//    }
    suspend fun greeting(): String {
        val response = httpClient.get("https://ktor.io/docs/")
        return response.bodyAsText()
    }
    @Throws(Exception::class)
    suspend fun greet(): String {
        val response =
            httpClient.get("http://10.0.2.2:8000/").body<String>()
        return response

    }

    @Throws(Exception::class)
    suspend fun signIn(username : String, password : String): String {
        val response =
            httpClient.post("http://10.0.2.2:8000/token"){
                headers {
                    append(HttpHeaders.Accept, "application/json")
                    append(HttpHeaders.ContentType, "application/x-www-form-urlencoded")
                }
                val requestBody = "grant_type=&username=$username&password=$password&scope=&client_id=&client_secret="
                setBody(requestBody)


            }.bodyAsText()
        token = Json.decodeFromString<UserToken>(response)
        return response

    }

    suspend fun getSportSession(): String {

        val response =
            httpClient.get("http://10.0.2.2:8000/sport-sessions/1"){
                headers {
                    append(HttpHeaders.Accept, "application/json")
                    append(HttpHeaders.Authorization, ("bearer " + token?.token))
                }


            }.bodyAsText()
        val json = Json { ignoreUnknownKeys = true }
        var sportSession = json.decodeFromString<SportSession>(response)
        return response
    }


}