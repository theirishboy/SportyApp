package com.example.yoursportapp.data

import com.example.yoursportapp.localstorage.KeyValueStorageImpl
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
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

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

expect val ApiUrl:String 
class UserDatabaseDAO(private val httpClient: HttpClient = HttpClientProvider.httpClient) : KoinComponent   {

    private val keyValueStorage: KeyValueStorageImpl by inject()

    @Throws(Exception::class)
        suspend fun isServerUp(): String {
        val response =
            httpClient.get(ApiUrl).body<String>()
        return response

    }
    @Throws(Exception::class)
    suspend fun signIn(username : String, password : String) : SignInResult{
        var response = ""
        try {
            response =
                httpClient.post(ApiUrl + ApiRoutes.TOKEN){
                    headers {
                        append(HttpHeaders.Accept, "application/json")
                        append(HttpHeaders.ContentType, "application/x-www-form-urlencoded")
                    }
                    val requestBody = "grant_type=&username=$username&password=$password&scope=&client_id=&client_secret="
                    setBody(requestBody)


                }.bodyAsText()
            return if (response.contains("token")) {

                val userToken:  UserToken  = Json.decodeFromString(response)
                keyValueStorage.token = userToken.token
                SignInResult.Success(response)
            } else {
                SignInResult.Error("false username or password")
            }
        }   catch (e: IOException) {
            return SignInResult.Error("Can't Connect to server + $e")
        }  catch(e : Exception){
            return SignInResult.Error("Can't connect to server, it might be down look at : $e")
        }
    }
    suspend fun signUp(firstname : String, lastname: String, email : String, password: String): SignUpResult {
        var response = ""
        try {
            response =
                httpClient.post(ApiUrl + ApiRoutes.CREATE_USER){
                    headers {
                        append(HttpHeaders.Accept, "application/json")
                        append(HttpHeaders.ContentType, "application/json")
                    }
                    setBody(User(email = email, password = password, firstname = firstname, lastname = lastname))


                }.bodyAsText()

            return if (response.contains("token"))
            {
                SignUpResult.Success(Json.decodeFromString(response))

            } else {
                SignUpResult.Error("Something is wrong")
            }
        }   catch (e: IOException) {
            println("Caught IOException: $e")
            return SignUpResult.Error( "NetworkException")
        }  catch(e : Exception){
            return SignUpResult.Error("Can't connect to server, it might be down look at : $e")
        }

    }
    @Throws(Exception::class)
    suspend fun getOneSportSession(): String {

        val response =
            httpClient.get(ApiUrl + ApiRoutes.SPORT_SESSION_BY_ID + "/1"){
                headers {
                    append(HttpHeaders.Accept, "application/json")
                    append(HttpHeaders.Authorization, ("bearer " + keyValueStorage.token ))
                }


            }.bodyAsText()
        val json = Json { ignoreUnknownKeys = true }
        var sportSession = json.decodeFromString<SportSession>(response)
        return response
    }

    @Throws(Exception::class)
    suspend fun getAllSportSessionFromUser(): MutableList<SportSession> {
        var result = arrayListOf<SportSession>()
        var response = ""
        try {
             response =
                httpClient.get(ApiUrl + ApiRoutes.SPORT_SESSION_ALL) {
                    headers {
                        append(HttpHeaders.Accept, "application/json")
                        append(HttpHeaders.Authorization, ("bearer " + keyValueStorage.token))
                    }


                }.bodyAsText()
        }
        catch (e : Exception){
            println("Caught Exception: $e")
        }
        val json = Json { ignoreUnknownKeys = true }
        try {
            println(result)
            result = json.decodeFromString(response)
        } catch (e : IOException){
            println("Caught IOException: $e")

        } catch (e : Exception){
            println("Caught IOException: $e")}

        return result
    }

}
sealed class SignInResult {
    data class Success(val token: String) : SignInResult()
    data class Error(val errorMessage: String) : SignInResult()
}
sealed class SignUpResult {
    data class Success(val listOfSportSessions: MutableList<SportSession> ) : SignUpResult()
    data class Error(val errorMessage: String) : SignUpResult()
}