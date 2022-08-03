package beresident.prototype.beresidentuserapp.screens.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import beresident.prototype.beresidentuserapp.MainActivity
import beresident.prototype.beresidentuserapp.core.misc.Screen
import beresident.prototype.beresidentuserapp.screens.splash.widgets.SplashView
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController){
    SplashView()
    LaunchedEffect(key1 = true, block = {
        delay(3000)
        navController.popBackStack()
        navController.navigate(Screen.LoginScreen.route)
    })

}
