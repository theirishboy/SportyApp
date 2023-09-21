import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import com.example.yoursportapp.initKoin
import com.example.yoursportapp.ui.screen.SignInScreen
import org.koin.compose.KoinApplication

@Composable
fun MainAppMpp() {
        MaterialTheme {
            Navigator(
                screen =  SignInScreen(0)

            )
        }
}

