package com.example.yoursportapp.Module

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import com.example.yoursportapp.data.UserDatabaseDAO

val commonModule = module {
    singleOf(::UserDatabaseDAO)
}