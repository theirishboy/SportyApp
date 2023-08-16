package com.example.yoursportapp

import app.cash.sqldelight.db.SqlDriver
import com.example.yoursportapp.SportDatabase

expect class DriverFactory {
    fun createDriver(): SqlDriver
}

fun createDatabase(driverFactory: DriverFactory): SportDatabase {
    val driver = driverFactory.createDriver()
    val database = SportDatabase(driver)

    // Do more work with the database (see below).
    return database
}