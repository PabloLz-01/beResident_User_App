package beresident.prototype.beresidentuserapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.collectAsState
import beresident.prototype.beresidentuserapp.core.misc.Navigation
import beresident.prototype.beresidentuserapp.core.misc.StoreTheme
import beresident.prototype.beresidentuserapp.screens.forgot.ForgotViewModel
import beresident.prototype.beresidentuserapp.screens.login.LoginViewModel
import beresident.prototype.beresidentuserapp.screens.register.RegisterViewModel
import beresident.prototype.beresidentuserapp.ui.theme.DefaultTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val loginViewModel: LoginViewModel by viewModels()
    private val registerViewModel: RegisterViewModel by viewModels()
    private val forgotViewModel: ForgotViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val dataStore = StoreTheme(this)
        super.onCreate(savedInstanceState)
        setContent {
            val themeValue = dataStore.getTheme.collectAsState(initial = 0)
            var theme = isSystemInDarkTheme()
            when (themeValue.value) {
                0 -> theme = isSystemInDarkTheme()
                1 -> theme = false
                2 -> theme = true
            }
            DefaultTheme(darkTheme = theme ) {
                Navigation(
                    loginViewModel,
                    registerViewModel,
                    forgotViewModel
                )
            }
        }
    }
}


