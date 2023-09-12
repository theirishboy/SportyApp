import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import com.example.yoursportapp.Module.appModule
import com.example.yoursportapp.data.UserDatabaseDAO
import com.example.yoursportapp.initKoin
import com.example.yoursportapp.ui.screen.SignInForm
import com.example.yoursportapp.ui.screen.SignInScreen
import com.example.yoursportapp.ui.screen.SignInViewModel
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.koin.compose.KoinApplication
import org.koin.core.context.startKoin

@Composable
fun MainAppMpp() {
    KoinApplication(application = {
        initKoin()
    }) {
        MaterialTheme {
            Navigator(
                screen =  SignInScreen(0)

            )
        }
    }

}

