package com.example.yoursportapp.android

import MainView
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.yoursportapp.android.ui.screen.SignInForm
import com.example.yoursportapp.android.ui.theme.AppTheme
import com.example.yoursportapp.data.UserDatabaseDAO
import com.example.yoursportapp.initKoin
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext

class MainActivity : ComponentActivity() {
    private val userDao: UserDatabaseDAO by inject()
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        initKoin {
            androidContext(applicationContext)
        }

        setContent {
            AppTheme {
                MainView()
            }
        }
    }
}
