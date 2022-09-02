package beresident.prototype.beresidentuserapp

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.*
import beresident.prototype.beresidentuserapp.core.misc.Navigation
import beresident.prototype.beresidentuserapp.core.misc.StoreTheme
import beresident.prototype.beresidentuserapp.core.services.BiometricService
import beresident.prototype.beresidentuserapp.screens.forgot.ForgotViewModel
import beresident.prototype.beresidentuserapp.screens.login.LoginViewModel
import beresident.prototype.beresidentuserapp.screens.register.RegisterViewModel
import beresident.prototype.beresidentuserapp.ui.theme.DefaultTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val loginViewModel: LoginViewModel by viewModels()
    private val registerViewModel: RegisterViewModel by viewModels()
    private val forgotViewModel: ForgotViewModel by viewModels()

    private val biometricService = BiometricService(this, this, )

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
                val scope = rememberCoroutineScope()
                Navigation(
                    loginViewModel,
                    registerViewModel,
                    forgotViewModel,
                    authentication = { biometricService.authenticate(scope) }
                )
            }
        }
        biometricService.setupAuth()
    }
}


