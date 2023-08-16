package com.example.yoursportapp

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import org.koin.core.module.Module
import org.koin.dsl.module


actual val driverFactory: Module = module {
    single<SqlDriver> {
        AndroidSqliteDriver(
            SportDatabase.Schema,
            get(),
            "SportDatabase"
        )
    }

}

actual class DriverFactory {
    actual fun createDriver(): SqlDriver {
        TODO("Not yet implemented")
    }
}