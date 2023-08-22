import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.yoursportapp.data.UserDatabaseDAO
import com.example.yoursportapp.ui.SignInForm
import com.example.yoursportapp.ui.SignInViewModel
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
@Composable
fun MainAppMpp() {
    MaterialTheme {
        var greetingText by remember { mutableStateOf("Hello, World!") }
        var showImage by remember { mutableStateOf(false) }
        val signInViewModel = getViewModel(Unit, viewModelFactory { SignInViewModel(UserDatabaseDAO()) })

        SignInForm(signInViewModel)
    }
}

