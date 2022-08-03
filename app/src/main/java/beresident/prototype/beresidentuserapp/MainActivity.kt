package beresident.prototype.beresidentuserapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import beresident.prototype.beresidentuserapp.core.misc.Navigation
import beresident.prototype.beresidentuserapp.ui.theme.DefaultTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DefaultTheme (darkTheme = true){
                Navigation()
            }
        }
    }
}

