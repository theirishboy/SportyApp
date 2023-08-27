import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.example.yoursportapp.data.UserDatabaseDAO
import com.example.yoursportapp.ui.screen.SignInForm
import com.example.yoursportapp.ui.screen.SignInViewModel
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
@Composable
fun MainAppMpp() {
    MaterialTheme {

        val signInViewModel = getViewModel(Unit, viewModelFactory { SignInViewModel(UserDatabaseDAO()) })

        SignInForm(signInViewModel)
    }
}

