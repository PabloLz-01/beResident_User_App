package beresident.prototype.beresidentuserapp.screens.prueba

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import beresident.prototype.beresidentuserapp.BaseApplication
import beresident.prototype.beresidentuserapp.ui.theme.DefaultTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@AndroidEntryPoint
class Prueba(navController: NavController) : ComponentActivity() {
    @Inject
    lateinit var application: BaseApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            ComposeView(this).apply {
                setContent {
                    Button(onClick = {}){
                        Text("oiauhadiuasd")
                    }
                }
            }
        )
    }
}

