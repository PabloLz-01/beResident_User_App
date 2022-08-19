package beresident.prototype.beresidentuserapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.collectAsState
import beresident.prototype.beresidentuserapp.core.misc.Navigation
import beresident.prototype.beresidentuserapp.core.misc.StoreTheme
import beresident.prototype.beresidentuserapp.core.misc.StoreUserCredentials
import beresident.prototype.beresidentuserapp.ui.theme.DefaultTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
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
                Navigation(this)
            }
        }
    }
}


