package com.example.yoursportapp


import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import org.koin.dsl.module

actual val driverFactory = module {
    single<SqlDriver> { NativeSqliteDriver(SportDatabase.Schema, "SportDatabase") }
}