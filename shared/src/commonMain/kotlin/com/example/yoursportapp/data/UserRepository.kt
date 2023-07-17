package com.example.yoursportapp.data


interface UserRepository {
    fun findUser(name : String): UserKoinTest?
    fun addUsers(users : List<UserKoinTest>)
}

class UserRepositoryImpl : UserRepository {

    private val _users = arrayListOf<UserKoinTest>()

    override fun findUser(name: String): UserKoinTest? {
        return _users.firstOrNull { it.name == name }
    }

    override fun addUsers(users : List<UserKoinTest>) {
        _users.addAll(users)
    }
}