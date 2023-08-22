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
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val userDao: UserDatabaseDAO by inject()
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                MainView()
            }
           /* AppTheme {
                SignInForm()
               /* Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var text by remember { mutableStateOf("Loading") }
                    LaunchedEffect(true) {
                        text = try {
                            userDao.isServerUp()
                            userDao.signIn("toto", "string")
                            userDao.getSportSession()

                        } catch (e: Exception) {
                            e.localizedMessage ?: "error"
                        }
                    }
                    GreetingView(text)
                }*/
            }*/
        }
    }
}

@Composable
fun GreetingView(text: String) {
    Text(text = text)
}

@Preview
@Composable
fun DefaultPreview() {
    AppTheme {
        GreetingView("Hello, Android!")
    }
}
