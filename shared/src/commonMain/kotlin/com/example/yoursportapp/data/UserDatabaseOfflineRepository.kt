package com.example.yoursportapp.data

import app.cash.sqldelight.db.SqlDriver
import com.example.yoursportapp.SportDatabase
import kotlinx.coroutines.flow.Flow
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import comexampleyoursportapp.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn

class UserDatabaseOfflineRepository(
    sqlDriver: SqlDriver,
    private val backgroundDispatcher: CoroutineDispatcher
) {
    private val dbref: SportDatabase = SportDatabase(sqlDriver)
    fun selectAllUser(): Flow<List<User>> =
        dbref.userQueries
            .selectAllLUserInfo()
            .asFlow()
            .mapToList(Dispatchers.Default)
            .flowOn(backgroundDispatcher)

    fun selectSportSession(){
        dbref.userQueries.selectAllLUserInfo()
    }
   fun insertUser(user: User){
        dbref.userQueries.insertUser(user.id,user.email,user.password,user.firstname,user.lastname,user.is_active,user.is_admin)
    }

}

