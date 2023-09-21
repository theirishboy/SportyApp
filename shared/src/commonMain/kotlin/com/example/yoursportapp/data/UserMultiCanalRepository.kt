package com.example.yoursportapp.data

import comexampleyoursportapp.User
import kotlinx.coroutines.flow.Flow

class UserMultiCanalRepository(
    private val dbHelper : UserDatabaseOfflineRepository,
    private val userApi: UserDatabaseDAO
) {

    fun getUsersFromStorage(): Flow<List<User>> = dbHelper.selectAllUser()
    suspend fun userConnection(username:String, password:String) {
        userApi.signIn(username, password)
    }
    suspend fun getUserSportSession(username:String, password:String) {
        userApi.getOneSportSession()
    }
    suspend fun isServerUp(username:String, password:String) {
        userApi.isServerUp()
    }
    fun selectAllUser(username:String, password:String) {
        dbHelper.selectAllUser()
    }
}