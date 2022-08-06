package beresident.prototype.beresidentuserapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import beresident.prototype.beresidentuserapp.core.misc.Navigation
import beresident.prototype.beresidentuserapp.core.misc.StoreTheme
import beresident.prototype.beresidentuserapp.ui.theme.DefaultTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val dataStore = StoreTheme(this)
        super.onCreate(savedInstanceState)
        setContent {
            val isDarkTheme = dataStore.getTheme.collectAsState(initial = false)

            DefaultTheme(darkTheme = isDarkTheme.value!!) {
                Navigation()
            }
        }
    }
}


