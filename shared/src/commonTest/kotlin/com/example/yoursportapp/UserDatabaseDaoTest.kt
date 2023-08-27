package com.example.yoursportapp

import com.example.yoursportapp.data.ApiRoutes
import com.example.yoursportapp.data.UserDatabaseDAO
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.content.TextContent
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.ByteReadChannel
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

private val responseHeaders = headersOf(HttpHeaders.ContentType,"application/json")
class ApiClientTest
{

    private lateinit var httpClient: HttpClient

    @BeforeTest
    fun setUp() {
        httpClient = HttpClient(MockEngine) {
            engine {
                addHandler { request ->
                    when(request.url.encodedPath) {
                       ApiRoutes.TOKEN -> {
                           if((request.body as TextContent).text == "grant_type=&username=test&password=test&scope=&client_id=&client_secret=")
                           {
                               respond(
                                   """{"access_token":"testToken","token_type":"bearer"}""",
                                   HttpStatusCode.OK,
                                   responseHeaders

                               )
                           } else{
                               respond(
                                   """{"detail":"Incorrect username or password"}""",
                                   HttpStatusCode.OK,
                                   responseHeaders

                               )
                           }

                       }
                        else -> error("Unhandled ${request.url.encodedPath}")

                    }
              }
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


    @Test
    fun signInWithValidCredentials() = runBlocking {
        val userDatabaseDAO = UserDatabaseDAO(httpClient)
        val result = userDatabaseDAO.signIn("test", "test")
        assertEquals("""{"access_token":"testToken","token_type":"bearer"}""", result)
    }

    @Test
    fun signInWithInvalidCredentials() = runBlocking {
        val userDatabaseDAO = UserDatabaseDAO(httpClient)
        val result = userDatabaseDAO.signIn("invalid", "creds")
        println(result)
        assertEquals("""{"detail":"Incorrect username or password"}""", result)
    }

}
